package ru.hoff.edu.service.command.handler;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.hoff.edu.domain.Parcel;
import ru.hoff.edu.domain.Truck;
import ru.hoff.edu.dto.LoadParcelsCommandDto;
import ru.hoff.edu.service.ParcelService;
import ru.hoff.edu.service.ReportWriter;
import ru.hoff.edu.service.command.Command;
import ru.hoff.edu.service.factory.LoadStrategyFactory;
import ru.hoff.edu.service.factory.ReportWriterFactory;
import ru.hoff.edu.service.strategy.LoadStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
            Optional<Parcel> parcel = parcelService.findByName(parcelId);
            if (!parcel.isPresent()) {
                continue;
            }

            parcels.add(parcel.get());
        }

        List<Truck> trucks = Truck.createTrucksByDescription(loadParcelsCommandDto.getTrucksDescriptions());

        LoadStrategy strategy = loadStrategyFactory.createStrategy(loadParcelsCommandDto.getAlgorithmType());
        List<Truck> result = strategy.loadParcels(parcels, trucks);

        ReportWriter reportWriter = reportWriterFactory.createReportWriter(loadParcelsCommandDto.getResultOutType(), loadParcelsCommandDto.getPathToResultFile());
        return reportWriter.write(result);
    }
}
