package ru.hoff.edu.service.command.impl;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
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

/**
 * Класс, реализующий обработку команды загрузки посылок (Parcels) в грузовики (Trucks).
 * Использует фабрики для создания стратегий загрузки и писателей отчетов, а также сервис для работы с посылками.
 */
@Slf4j
@Getter
@Component
@RequiredArgsConstructor
public class LoadParcelsCommand implements Command<String, LoadParcelsCommandDto> {

    private final ReportWriterFactory reportWriterFactory;
    private final LoadStrategyFactory loadStrategyFactory;
    private final ParcelService parcelService;

    /**
     * Выполняет команду загрузки посылок в грузовики.
     *
     * @param loadParcelsCommandDto DTO команды, содержащее данные для загрузки посылок.
     * @return Результат выполнения команды в виде строки (например, отчет или сообщение об ошибке).
     * @throws IllegalArgumentException если список посылок пуст.
     */
    @Override
    public String execute(LoadParcelsCommandDto loadParcelsCommandDto) {
        if (loadParcelsCommandDto.getParcelIds().isEmpty()) {
            log.info("No packages found");
            throw new IllegalArgumentException("No packages found");
        }

        List<Parcel> parcels = new ArrayList<>();
        for (String parcelId : loadParcelsCommandDto.getParcelIds()) {
            Optional<Parcel> parcel = parcelService.findByName(parcelId);
            if (parcel.isEmpty()) {
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
