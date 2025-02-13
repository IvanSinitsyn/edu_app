package ru.hoff.edu.model;

import lombok.Getter;
import ru.hoff.edu.domain.Parcel;
import ru.hoff.edu.domain.Truck;

import java.util.List;

@Getter
public class TruckData {

    private Truck truck;

    public List<Parcel> getParcels() {
        return truck.getParcels();
    }
}
