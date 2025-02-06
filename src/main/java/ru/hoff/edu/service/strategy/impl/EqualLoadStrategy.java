package ru.hoff.edu.service.strategy.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.hoff.edu.domain.Parcel;
import ru.hoff.edu.domain.Truck;
import ru.hoff.edu.service.DataConverter;
import ru.hoff.edu.service.ParcelService;
import ru.hoff.edu.service.strategy.LoadStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс, реализующий стратегию равномерной загрузки посылок в грузовики.
 * Посылки распределяются по грузовикам равномерно, с учетом их вместимости.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class EqualLoadStrategy implements LoadStrategy {

    private final ParcelService parcelService;
    private final DataConverter dataConverter;

    /**
     * Загружает посылки в грузовики по стратегии равномерной загрузки.
     * Если грузовики не предоставлены, создает необходимое количество грузовиков.
     *
     * @param parcels Список посылок, которые необходимо загрузить.
     * @param trucks  Список грузовиков, в которые будут загружены посылки.
     * @return Список грузовиков с загруженными посылками.
     */
    @Override
    public List<Truck> loadParcels(List<Parcel> parcels, List<Truck> trucks) {
        if (trucks == null || trucks.isEmpty()) {
            return loadUnlimitedTrucks(parcels);
        }

        return loadLimitTrucks(parcels, trucks);
    }

    private List<Truck> loadLimitTrucks(List<Parcel> parcels, List<Truck> trucks) {
        log.info("Start loading equally mode with limited trucks");

        for (Parcel parcel : parcels) {
            log.info("Loading package {}", dataConverter.parcelToString(parcel));

            Truck suitableTruck = parcelService.findSuitableTruck(trucks, parcel);
            if (suitableTruck != null) {
                parcelService.placeParcelInTruck(suitableTruck, parcel);
                parcel.setIsLoaded(true);
                log.info("Package {} loaded", dataConverter.parcelToString(parcel));
            } else {
                log.info("Cannot find suitable truck for parcel: {}", dataConverter.parcelToString(parcel));
                throw new IllegalArgumentException("Cannot find suitable truck for parcel: " + dataConverter.parcelToString(parcel));
            }
        }

        log.info("Loading with limited trucks completed");
        return trucks;
    }

    private List<Truck> loadUnlimitedTrucks(List<Parcel> parcels) {
        log.info("Start loading equally mode with unlimited trucks");
        List<Truck> trucks = new ArrayList<>();

        for (Parcel parcel : parcels) {
            log.info("Loading package {}", dataConverter.parcelToString(parcel));

            Truck suitableTruck = parcelService.findSuitableTruck(trucks, parcel);
            if (suitableTruck != null) {
                parcelService.placeParcelInTruck(suitableTruck, parcel);
            } else {
                Truck newTruck = new Truck();
                trucks.add(newTruck);
                parcelService.placeParcelInTruck(newTruck, parcel);
            }

            parcel.setIsLoaded(true);
            log.info("Package {} loaded", dataConverter.parcelToString(parcel));
        }

        log.info("Loading with unlimited trucks completed");
        return trucks;
    }
}
