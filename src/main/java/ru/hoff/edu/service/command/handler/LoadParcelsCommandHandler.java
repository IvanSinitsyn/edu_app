package ru.hoff.edu.service.command.handler;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.hoff.edu.domain.Parcel;
import ru.hoff.edu.domain.Truck;
import ru.hoff.edu.dto.LoadParcelsCommandDto;
import ru.hoff.edu.service.LoadStrategy;
import ru.hoff.edu.service.LoadStrategyFactory;
import ru.hoff.edu.service.ParcelService;
import ru.hoff.edu.service.ReportWriter;
import ru.hoff.edu.service.ReportWriterFactory;
import ru.hoff.edu.service.command.Command;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Getter
@RequiredArgsConstructor
public class LoadParcelsCommandHandler implements Command<String, LoadParcelsCommandDto> {

    private final ReportWriterFactory reportWriterFactory;
    private final LoadStrategyFactory loadStrategyFactory;
    private final ParcelService parcelService;

    @Override
    public String execute(LoadParcelsCommandDto loadParcelsCommandDto) {
        if (loadParcelsCommandDto.getParcelIds().isEmpty()) {
            log.info("No packages found");
            throw new IllegalArgumentException("No packages found");
        }

        List<Parcel> parcels = new ArrayList<>();
        for (String parcelId : loadParcelsCommandDto.getParcelIds()) {
            Parcel parcel = parcelService.findByName(parcelId);
            if (parcel == null) {
                continue;
            }

            parcels.add(parcel);
        }

        List<Truck> trucks = Truck.createTrucksByDescription(loadParcelsCommandDto.getTrucksDescriptions());

        LoadStrategy strategy = loadStrategyFactory.createStrategy(loadParcelsCommandDto.getAlgorithmType());
        List<Truck> result = strategy.loadParcels(parcels, trucks);

        ReportWriter reportWriter = reportWriterFactory.createReportWriter(loadParcelsCommandDto.getResultOutType(), loadParcelsCommandDto.getPathToResultFile());
        return reportWriter.writeReport(result);
    }
}
