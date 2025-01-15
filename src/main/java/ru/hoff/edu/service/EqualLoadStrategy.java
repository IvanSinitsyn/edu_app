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
public class EqualLoadStrategy implements LoadStrategy {

    private final ParcelService parcelService;

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
            log.info("Loading package {}", DataConverter.parcelToString(parcel));

            Truck suitableTruck = parcelService.findSuitableTruck(trucks, parcel);
            if (suitableTruck != null) {
                parcelService.placeParcelInTruck(suitableTruck, parcel);
                parcel.setLoaded(true);
                log.info("Package {} loaded", DataConverter.parcelToString(parcel));
            } else {
                log.info("Cannot find suitable truck for parcel: {}", DataConverter.parcelToString(parcel));
                throw new IllegalArgumentException("Cannot find suitable truck for parcel: " + DataConverter.parcelToString(parcel));
            }
        }

        log.info("Loading with limited trucks completed");
        return trucks;
    }

    private List<Truck> loadUnlimitedTrucks(List<Parcel> parcels) {
        log.info("Start loading equally mode with unlimited trucks");
        List<Truck> trucks = new ArrayList<>();

        for (Parcel parcel : parcels) {
            log.info("Loading package {}", DataConverter.parcelToString(parcel));

            Truck suitableTruck = parcelService.findSuitableTruck(trucks, parcel);
            if (suitableTruck != null) {
                parcelService.placeParcelInTruck(suitableTruck, parcel);
            } else {
                Truck newTruck = new Truck();
                trucks.add(newTruck);
                parcelService.placeParcelInTruck(newTruck, parcel);
            }

            parcel.setLoaded(true);
            log.info("Package {} loaded", DataConverter.parcelToString(parcel));
        }

        log.info("Loading with unlimited trucks completed");
        return trucks;
    }
}
