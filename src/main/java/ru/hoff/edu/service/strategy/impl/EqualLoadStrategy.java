package ru.hoff.edu.service.strategy.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.hoff.edu.domain.Parcel;
import ru.hoff.edu.domain.Truck;
import ru.hoff.edu.service.core.TruckService;
import ru.hoff.edu.service.mapper.ParcelMapper;
import ru.hoff.edu.service.strategy.LoadStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Класс, реализующий стратегию равномерной загрузки посылок в грузовики.
 * Посылки распределяются по грузовикам равномерно, с учетом их вместимости.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class EqualLoadStrategy implements LoadStrategy {

    private final ParcelMapper parcelMapper;
    private final TruckService truckService;

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
            log.info("Loading package {}", parcelMapper.parcelToString(parcel));

            Optional<Truck> suitableTruck = truckService.findSuitableTruck(trucks, parcel);
            if (suitableTruck.isPresent()) {
                truckService.placeParcelInTruck(suitableTruck.get(), parcel);
                parcel.setIsLoaded(true);
                log.info("Package {} loaded", parcelMapper.parcelToString(parcel));
            } else {
                log.info("Cannot find suitable truck for parcel: {}", parcelMapper.parcelToString(parcel));
                throw new IllegalArgumentException("Cannot find suitable truck for parcel: " + parcelMapper.parcelToString(parcel));
            }
        }

        log.info("Loading with limited trucks completed");
        return trucks;
    }

    private List<Truck> loadUnlimitedTrucks(List<Parcel> parcels) {
        log.info("Start loading equally mode with unlimited trucks");
        List<Truck> trucks = new ArrayList<>();

        for (Parcel parcel : parcels) {
            log.info("Loading package {}", parcelMapper.parcelToString(parcel));

            Optional<Truck> suitableTruck = truckService.findSuitableTruck(trucks, parcel);
            if (suitableTruck.isPresent()) {
                truckService.placeParcelInTruck(suitableTruck.get(), parcel);
            } else {
                Truck newTruck = new Truck();
                trucks.add(newTruck);
                truckService.placeParcelInTruck(newTruck, parcel);
            }

            parcel.setIsLoaded(true);
            log.info("Package {} loaded", parcelMapper.parcelToString(parcel));
        }

        log.info("Loading with unlimited trucks completed");
        return trucks;
    }
}
