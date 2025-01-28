package ru.hoff.edu.service.command.impl;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.hoff.edu.domain.Parcel;
import ru.hoff.edu.domain.Truck;
import ru.hoff.edu.dto.LoadParcelsCommandDto;
import ru.hoff.edu.dto.response.LoadParcelsResponseDto;
import ru.hoff.edu.service.ParcelService;
import ru.hoff.edu.service.ReportWriter;
import ru.hoff.edu.service.command.Command;
import ru.hoff.edu.service.exception.ParcelNotFoundException;
import ru.hoff.edu.service.factory.LoadStrategyFactory;
import ru.hoff.edu.service.factory.ReportWriterFactory;
import ru.hoff.edu.service.strategy.LoadStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс, реализующий обработку команды загрузки посылок (Parcels) в грузовики (Trucks).
 * Использует фабрики для создания стратегий загрузки и писателей отчетов, а также сервис для работы с посылками.
 */
@Slf4j
@Getter
@Component
@RequiredArgsConstructor
public class LoadParcelsCommand implements Command<LoadParcelsResponseDto, LoadParcelsCommandDto> {

    private final ReportWriterFactory reportWriterFactory;
    private final LoadStrategyFactory loadStrategyFactory;
    private final ParcelService parcelService;

    /**
     * Выполняет команду загрузки посылок в грузовики.
     *
     * @param loadParcelsCommandDto DTO команды, содержащее данные для загрузки посылок.
     * @return Результат выполнения команды в виде DTO LoadParcelsResponseDto (например, отчет или сообщение об ошибке).
     * @see LoadParcelsResponseDto
     * @throws IllegalArgumentException если список посылок пуст.
     */
    @Override
    public LoadParcelsResponseDto execute(LoadParcelsCommandDto loadParcelsCommandDto) {
        if (loadParcelsCommandDto.parcelIds().isEmpty()) {
            log.info("No packages found");
            throw new IllegalArgumentException("No packages found");
        }

        List<Parcel> parcels = new ArrayList<>();
        for (String parcelId : loadParcelsCommandDto.parcelIds()) {
            Parcel parcel;
            try {
                parcel = parcelService.findByName(parcelId);
            } catch (ParcelNotFoundException e) {
                log.info("Parcel not found: {}", parcelId);
                continue;
            }

            parcels.add(parcel);
        }

        List<Truck> trucks = Truck.createTrucksByDescription(loadParcelsCommandDto.trucksDescriptions());

        LoadStrategy strategy = loadStrategyFactory.createStrategy(loadParcelsCommandDto.algorithmType());
        List<Truck> loadResult = strategy.loadParcels(parcels, trucks);

        ReportWriter reportWriter = reportWriterFactory.createReportWriter(loadParcelsCommandDto.resultOutType(), loadParcelsCommandDto.pathToResultFile());
        String result = reportWriter.write(loadResult);
        return new LoadParcelsResponseDto(result);
    }
}
