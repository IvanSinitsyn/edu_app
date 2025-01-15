package ru.hoff.edu.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.hoff.edu.domain.Parcel;
import ru.hoff.edu.domain.Truck;
import ru.hoff.edu.util.DataConverter;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class OptimalLoadStrategy implements LoadStrategy {

    private final ParcelService parcelService;

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
            if (parcel.isLoaded()) {
                continue;
            }

            boolean loaded = false;
            while (truckIndex < trucks.size()) {
                Truck currentTruck = trucks.get(truckIndex);

                log.info("Trying to load package {} into truck {}", DataConverter.parcelToString(parcel), truckIndex);

                if (parcelService.tryPlacePackageInTruck(currentTruck, parcel)) {
                    parcelService.placeParcelInTruck(currentTruck, parcel);
                    parcel.setLoaded(true);
                    loaded = true;
                    log.info("Package {} loaded into truck {}", DataConverter.parcelToString(parcel), truckIndex);
                    break;
                } else {
                    truckIndex++;
                }
            }

            if (!loaded) {
                throw new IllegalArgumentException("Unable to load package " + DataConverter.parcelToString(parcel) + " into truck " + trucks.get(truckIndex - 1).showTruckSize());
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
            if (parcel.isLoaded()) {
                continue;
            }

            boolean loaded = false;
            while (truckIndex < trucks.size()) {
                Truck currentTruck = trucks.get(truckIndex);

                log.info("Trying to load package {} into truck {}", DataConverter.parcelToString(parcel), truckIndex);

                if (parcelService.tryPlacePackageInTruck(currentTruck, parcel)) {
                    parcelService.placeParcelInTruck(currentTruck, parcel);
                    parcel.setLoaded(true);
                    loaded = true;
                    log.info("Package {} loaded into truck {}", DataConverter.parcelToString(parcel), truckIndex);
                    break;
                } else {
                    truckIndex++;
                }
            }

            if (!loaded) {
                Truck newTruck = new Truck();
                if (parcelService.tryPlacePackageInTruck(newTruck, parcel)) {
                    parcelService.placeParcelInTruck(newTruck, parcel);
                    parcel.setLoaded(true);
                    trucks.add(newTruck);
                    log.info("Package {} loaded into new truck {}", DataConverter.parcelToString(parcel), trucks.size());
                } else {
                    log.warn("Package {} could not be loaded into any truck", DataConverter.parcelToString(parcel));
                    throw new IllegalArgumentException("Parcel " + DataConverter.parcelToString(parcel) + " was not loaded in trucks: " + String.join(", ", trucks.stream().map(Truck::showTruckSize).toList()));
                }
            }
        }

        log.info("Loading with unlimited trucks completed");
        return trucks;
    }
}
