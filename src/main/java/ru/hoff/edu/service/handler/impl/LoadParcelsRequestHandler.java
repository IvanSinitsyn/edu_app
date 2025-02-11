package ru.hoff.edu.service.handler.impl;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.hoff.edu.domain.Parcel;
import ru.hoff.edu.domain.Truck;
import ru.hoff.edu.model.dto.response.LoadParcelsResponseDto;
import ru.hoff.edu.model.enums.ChequeType;
import ru.hoff.edu.model.enums.FileType;
import ru.hoff.edu.service.FileExtensionParser;
import ru.hoff.edu.service.OutboxWriter;
import ru.hoff.edu.service.ParcelService;
import ru.hoff.edu.service.ReportWriter;
import ru.hoff.edu.service.TruckCreator;
import ru.hoff.edu.service.exception.ParcelNotFoundException;
import ru.hoff.edu.service.factory.FileReaderFactory;
import ru.hoff.edu.service.factory.LoadStrategyFactory;
import ru.hoff.edu.service.factory.ReportWriterFactory;
import ru.hoff.edu.service.filereader.InputFileReader;
import ru.hoff.edu.service.handler.RequestHandler;
import ru.hoff.edu.service.request.Request;
import ru.hoff.edu.service.request.impl.LoadParcelsRequest;
import ru.hoff.edu.service.strategy.LoadStrategy;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Getter
@Component
@RequiredArgsConstructor
public class LoadParcelsRequestHandler implements RequestHandler {

    private final ReportWriterFactory reportWriterFactory;
    private final LoadStrategyFactory loadStrategyFactory;
    private final ParcelService parcelService;
    private final TruckCreator truckCreator;
    private final FileReaderFactory fileReaderFactory;
    private final FileExtensionParser fileExtensionParser;
    private final OutboxWriter outboxWriter;

    @Override
    public Object handle(Request request) {
        LoadParcelsRequest loadRequest = (LoadParcelsRequest) request;

        List<String> parcelIds;
        if (!loadRequest.parcelIds().isEmpty()) {
            parcelIds = loadRequest.parcelIds();
        } else {
            InputFileReader fileReader = fileReaderFactory.createFileReader(
                    FileType.fromString(
                            fileExtensionParser.getFileExtension(loadRequest.pathToParcelsFile())));
            parcelIds = fileReader.readFile(loadRequest.pathToParcelsFile());
        }

        if (parcelIds.isEmpty()) {
            throw new IllegalArgumentException("No packages found");
        }

        List<Parcel> parcels = new ArrayList<>();
        for (String parcelId : parcelIds) {
            Parcel parcel;
            try {
                parcel = parcelService.findByName(parcelId);
            } catch (ParcelNotFoundException e) {
                log.info("Parcel not found: {}", parcelId);
                continue;
            }

            parcels.add(parcel);
        }

        List<Truck> trucks = truckCreator.createTrucksByDescription(loadRequest.trucksDescriptions());

        LoadStrategy strategy = loadStrategyFactory.createStrategy(loadRequest.algorithmType());
        List<Truck> loadResult = strategy.loadParcels(parcels, trucks);

        outboxWriter.write(loadRequest.userId(), loadResult, ChequeType.LOAD);

        ReportWriter reportWriter = reportWriterFactory.createReportWriter(loadRequest.resultOutType());
        String result = reportWriter.write(loadResult, loadRequest.pathToResultFile());
        return new LoadParcelsResponseDto(result);
    }
}
