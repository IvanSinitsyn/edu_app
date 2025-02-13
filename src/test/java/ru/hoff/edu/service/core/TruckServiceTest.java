package ru.hoff.edu.service.core;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hoff.edu.domain.Parcel;
import ru.hoff.edu.domain.Truck;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class TruckServiceTest {

    private final TruckService truckService = new TruckService();

    @Test
    void findSuitableTruck_ShouldReturnSuitableTruck_ForPlaceParcel() {
        Parcel parcel = new Parcel("Parcel1", new char[][]{{'A'}}, "A", false);
        Truck truck = new Truck();

        Optional<Truck> suitableTruck = truckService.findSuitableTruck(Collections.singletonList(truck), parcel);

        assertThat(suitableTruck.isPresent()).isTrue();
        assertThat(suitableTruck.get()).isEqualTo(truck);
    }

    @Test
    void tryPlacePackageInTruck_ShouldReturnTrue_WhenParcelCanBePlaced() {
        Parcel parcel = new Parcel("Parcel1", new char[][]{{'A'}}, "A", false);
        Truck truck = new Truck();

        boolean result = truckService.tryPlacePackageInTruck(truck, parcel);

        assertThat(result).isTrue();
    }

    @Test
    void placeParcelInTruck_ShouldPlaceParcelInTruck() {
        Parcel parcel = new Parcel("Parcel1", new char[][]{{'A'}}, "A", false);
        Truck truck = new Truck();

        truckService.placeParcelInTruck(truck, parcel);

        assertThat(truck.isEmpty()).isFalse();
    }
}
