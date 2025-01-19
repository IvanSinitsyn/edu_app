package ru.hoff.edu.service;

import lombok.extern.slf4j.Slf4j;
import ru.hoff.edu.domain.Parcel;
import ru.hoff.edu.domain.Truck;
import ru.hoff.edu.repository.ParcelRepository;
import ru.hoff.edu.validation.ParcelValidator;

import java.util.List;
import java.util.Optional;

@Slf4j
public class ParcelService {

    private final ParcelRepository parcelRepository;

    public ParcelService(ParcelRepository parcelRepository) {
        this.parcelRepository = parcelRepository;
    }

    public void add(Parcel parcel) {
        Optional<Parcel> existedParcel = parcelRepository.findParcelByName(parcel.getName());
        if (existedParcel.isPresent()) {
            throw new IllegalArgumentException("Посылка с таким именем уже существует");
        }

        if (!ParcelValidator.isParcelFormValid(parcel.getForm(), parcel.getSymbol().charAt(0))) {
            throw new IllegalArgumentException("Форма посылки невалидная");
        }

        parcel.redraw(parcel.getSymbol().charAt(0));
        parcelRepository.addParcel(parcel);
    }

    public List<Parcel> findAll() {
        return parcelRepository.findAllParcels();
    }

    public Optional<Parcel> findByName(String name) {
        return parcelRepository.findParcelByName(name);
    }

    public void delete(String name) {
        parcelRepository.deleteParcel(name);
    }

    public Parcel edit(String id, String newName, char[][] newForm, String newSymbol) {
        Optional<Parcel> existedParcel = parcelRepository.findParcelByName(id);
        if (!existedParcel.isPresent()) {
            throw new IllegalArgumentException("Посылка не найдена");
        }

        if (!ParcelValidator.isParcelFormValid(newForm, existedParcel.get().getSymbol().charAt(0))) {
            throw new IllegalArgumentException("Новая форма невалидная");
        }

        return parcelRepository.edit(id, newName, newForm, newSymbol);
    }

    public Truck findSuitableTruck(List<Truck> trucks, Parcel parcel) {
        for (Truck truck : trucks) {
            if (canFitInHalfTruck(truck, parcel)) {
                return truck;
            }
        }
        return null;
    }

    public boolean tryPlacePackageInTruck(Truck truck, Parcel parcel) {
        for (int y = 0; y <= Truck.HEIGHT - parcel.getHeight(); y++) {
            for (int x = 0; x <= Truck.WIDTH - parcel.getWidth(); x++) {
                if (truck.canPlace(parcel, x, y)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean canFitInHalfTruck(Truck truck, Parcel parcel) {
        int potentialNewLoad = truck.getCurrentLoad() + parcel.getWidth() * parcel.getHeight();
        return potentialNewLoad <= truck.getHalfCapacity() && tryPlacePackageInTruck(truck, parcel);
    }

    public void placeParcelInTruck(Truck truck, Parcel parcel) {
        for (int y = 0; y <= Truck.HEIGHT - parcel.getHeight(); y++) {
            for (int x = 0; x <= Truck.WIDTH - parcel.getWidth(); x++) {
                if (truck.canPlace(parcel, x, y)) {
                    truck.place(parcel, x, y);
                    return;
                }
            }
        }
    }
}
