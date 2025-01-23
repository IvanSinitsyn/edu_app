package ru.hoff.edu.service.strategy;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.hoff.edu.domain.Parcel;
import ru.hoff.edu.domain.Truck;
import ru.hoff.edu.service.ParcelService;
import ru.hoff.edu.util.DataConverter;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class EasyLoadStrategy implements LoadStrategy {

    private final ParcelService parcelService;

    @Override
    public List<Truck> loadParcels(List<Parcel> parcels, List<Truck> trucks) {
        if (trucks == null || trucks.isEmpty()) {
            trucks = new ArrayList<>();
            for (Parcel ignored : parcels) {
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
                if (!parcel.isLoaded() && parcelService.tryPlacePackageInTruck(truck, parcel)) {
                    parcelService.placeParcelInTruck(truck, parcel);
                    trucksResult.add(truck);
                    parcel.setLoaded(true);
                    log.info("Package {} loaded", DataConverter.parcelToString(parcel));
                    break;
                }

                throw new IllegalArgumentException("Parcel " + DataConverter.parcelToString(parcel) + " was not loaded in truck " + truck.getWidth() + "x" + truck.getHeight());
            }
        }

        log.info("Easy loading is completed");
        return trucksResult;
    }
}
