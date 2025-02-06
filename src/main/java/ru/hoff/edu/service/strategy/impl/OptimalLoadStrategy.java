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
 * Класс, реализующий оптимальную стратегию загрузки посылок в грузовики.
 * Посылки загружаются в грузовики с минимальным использованием свободного пространства.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class OptimalLoadStrategy implements LoadStrategy {

    private final ParcelService parcelService;
    private final DataConverter dataConverter;

    /**
     * Загружает посылки в грузовики по оптимальной стратегии.
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
        log.info("Loading with limited trucks");
        int truckIndex = 0;

        for (Parcel parcel : parcels) {
            if (parcel.getIsLoaded()) {
                continue;
            }

            boolean loaded = false;
            while (truckIndex < trucks.size()) {
                Truck currentTruck = trucks.get(truckIndex);

                log.info("Trying to load package {} into truck {}", dataConverter.parcelToString(parcel), truckIndex);

                if (parcelService.tryPlacePackageInTruck(currentTruck, parcel)) {
                    parcelService.placeParcelInTruck(currentTruck, parcel);
                    parcel.setIsLoaded(true);
                    loaded = true;
                    log.info("Package {} loaded into truck {}", dataConverter.parcelToString(parcel), truckIndex);
                    break;
                } else {
                    truckIndex++;
                }
            }

            if (!loaded) {
                throw new IllegalArgumentException("Unable to load package " + dataConverter.parcelToString(parcel) + " into truck " + trucks.get(truckIndex - 1).showTruckSize());
            }
        }

        log.info("Loading with limited trucks completed");
        return trucks;
    }

    private List<Truck> loadUnlimitedTrucks(List<Parcel> parcels) {
        List<Truck> trucks = new ArrayList<>();
        int truckIndex = 0;

        log.info("Loading with unlimited trucks");
        for (Parcel parcel : parcels) {
            if (parcel.getIsLoaded()) {
                continue;
            }

            boolean loaded = false;
            while (truckIndex < trucks.size()) {
                Truck currentTruck = trucks.get(truckIndex);

                log.info("Trying to load package {} into truck {}", dataConverter.parcelToString(parcel), truckIndex);

                if (parcelService.tryPlacePackageInTruck(currentTruck, parcel)) {
                    parcelService.placeParcelInTruck(currentTruck, parcel);
                    parcel.setIsLoaded(true);
                    loaded = true;
                    log.info("Package {} loaded into truck {}", dataConverter.parcelToString(parcel), truckIndex);
                    break;
                } else {
                    truckIndex++;
                }
            }

            if (!loaded) {
                Truck newTruck = new Truck();
                if (parcelService.tryPlacePackageInTruck(newTruck, parcel)) {
                    parcelService.placeParcelInTruck(newTruck, parcel);
                    parcel.setIsLoaded(true);
                    trucks.add(newTruck);
                    log.info("Package {} loaded into new truck {}", dataConverter.parcelToString(parcel), trucks.size());
                } else {
                    log.warn("Package {} could not be loaded into any truck", dataConverter.parcelToString(parcel));
                    throw new IllegalArgumentException("Parcel " + dataConverter.parcelToString(parcel) + " was not loaded in trucks: " + String.join(", ", trucks.stream().map(Truck::showTruckSize).toList()));
                }
            }
        }

        log.info("Loading with unlimited trucks completed");
        return trucks;
    }
}
