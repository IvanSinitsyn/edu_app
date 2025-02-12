package ru.hoff.edu.service.strategy.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.hoff.edu.domain.Parcel;
import ru.hoff.edu.domain.Truck;
import ru.hoff.edu.service.core.ParcelService;
import ru.hoff.edu.service.mapper.ParcelMapper;
import ru.hoff.edu.service.strategy.LoadStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс, реализующий простую стратегию загрузки посылок в грузовики.
 * Каждая посылка загружается в отдельный грузовик, если это возможно.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class EasyLoadStrategy implements LoadStrategy {

    private final ParcelService parcelService;
    private final ParcelMapper parcelMapper;

    /**
     * Загружает посылки в грузовики по простой стратегии.
     * Каждая посылка загружается в отдельный грузовик, если это возможно.
     *
     * @param parcels Список посылок, которые необходимо загрузить.
     * @param trucks  Список грузовиков, в которые будут загружены посылки.
     * @return Список грузовиков с загруженными посылками.
     * @throws IllegalArgumentException если количество посылок и грузовиков не совпадает или посылка не может быть загружена.
     */
    @Override
    public List<Truck> loadParcels(List<Parcel> parcels, List<Truck> trucks) {
        if (trucks == null || trucks.isEmpty()) {
            trucks = new ArrayList<>();
            while (trucks.size() != parcels.size()) {
                trucks.add(new Truck());
            }
        }

        if (parcels.size() != trucks.size()) {
            throw new IllegalArgumentException("Parcels and trucks must have the same count.");
        }

        List<Truck> trucksResult = new ArrayList<>();
        log.info("Start loading trucks with easy mode");
        for (Truck truck : trucks) {
            for (Parcel parcel : parcels) {
                if (parcel.getIsLoaded()) {
                    continue;
                }

                if (parcelService.tryPlacePackageInTruck(truck, parcel)) {
                    parcelService.placeParcelInTruck(truck, parcel);
                    trucksResult.add(truck);
                    parcel.setIsLoaded(true);
                    log.info("Package {} loaded", parcelMapper.parcelToString(parcel));
                    break;
                }

                throw new IllegalArgumentException("Parcel " + parcel.getName() + " was not loaded in truck " + truck.getHeight() + "x" + truck.getWidth());
            }
        }

        log.info("Easy loading is completed");
        return trucksResult;
    }
}
