package ru.hoff.edu.service.core;

import org.springframework.stereotype.Service;
import ru.hoff.edu.domain.Parcel;
import ru.hoff.edu.domain.Truck;

import java.util.List;
import java.util.Optional;

@Service
public class TruckService {

    /**
     * Ищет подходящий грузовик для размещения посылки.
     *
     * @param trucks Список грузовиков.
     * @param parcel Посылка, которую необходимо разместить.
     * @return Подходящий грузовик или {@code null}, если подходящий грузовик не найден.
     */
    public Optional<Truck> findSuitableTruck(List<Truck> trucks, Parcel parcel) {
        for (Truck truck : trucks) {
            if (canFitInHalfTruck(truck, parcel)) {
                return Optional.of(truck);
            }
        }
        return Optional.empty();
    }

    /**
     * Проверяет, можно ли разместить посылку в грузовике.
     *
     * @param truck  Грузовик, в который необходимо разместить посылку.
     * @param parcel Посылка, которую необходимо разместить.
     * @return {@code true}, если посылку можно разместить, иначе {@code false}.
     */
    public boolean tryPlacePackageInTruck(Truck truck, Parcel parcel) {
        for (int y = 0; y <= truck.getHeight() - parcel.getHeight(); y++) {
            for (int x = 0; x <= truck.getWidth() - parcel.getWidth(); x++) {
                if (truck.canPlace(parcel, x, y)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Проверяет, может ли посылка поместиться в грузовик, не превышая половину его вместимости.
     *
     * @param truck  Грузовик, в который необходимо разместить посылку.
     * @param parcel Посылка, которую необходимо разместить.
     * @return {@code true}, если посылка может поместиться, иначе {@code false}.
     */
    private boolean canFitInHalfTruck(Truck truck, Parcel parcel) {
        int potentialNewLoad = truck.getCurrentLoad() + parcel.getWidth() * parcel.getHeight();
        return potentialNewLoad <= truck.getHalfCapacity() && tryPlacePackageInTruck(truck, parcel);
    }

    /**
     * Размещает посылку в грузовике.
     *
     * @param truck  Грузовик, в который необходимо разместить посылку.
     * @param parcel Посылка, которую необходимо разместить.
     */
    public void placeParcelInTruck(Truck truck, Parcel parcel) {
        for (int y = 0; y <= truck.getHeight() - parcel.getHeight(); y++) {
            for (int x = 0; x <= truck.getWidth() - parcel.getWidth(); x++) {
                if (truck.canPlace(parcel, x, y)) {
                    truck.place(parcel, x, y);
                    return;
                }
            }
        }
    }
}
