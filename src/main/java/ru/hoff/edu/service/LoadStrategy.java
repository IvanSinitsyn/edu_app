package ru.hoff.edu.service;

import ru.hoff.edu.domain.Parcel;
import ru.hoff.edu.domain.Truck;

import java.util.List;

public interface LoadStrategy {

    List<Truck> loadParcels(List<Parcel> parcels, List<Truck> trucks);
}
