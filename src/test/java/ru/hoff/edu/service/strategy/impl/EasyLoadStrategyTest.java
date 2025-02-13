package ru.hoff.edu.service.strategy.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.hoff.edu.domain.Parcel;
import ru.hoff.edu.domain.Truck;
import ru.hoff.edu.service.core.TruckService;
import ru.hoff.edu.service.mapper.ParcelMapper;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class EasyLoadStrategyTest {

    @Mock
    private TruckService truckService;

    @Mock
    private ParcelMapper parcelMapper;

    @InjectMocks
    private EasyLoadStrategy easyLoadStrategy;

    private List<Parcel> parcels;
    private List<Truck> trucks;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        char[][] form = new char[][]{{'A', 'A'}, {'A', 'A'}};

        parcels = List.of(
                new Parcel("Parcel1", form, "A", false),
                new Parcel("Parcel2", form, "A", false),
                new Parcel("Parcel3", form, "A", false)
        );

        trucks = List.of(new Truck(), new Truck(), new Truck());
    }

    @Test
    void loadParcels_shouldLoadParcelsIntoTrucks_whenValidInput() {
        when(truckService.tryPlacePackageInTruck(any(Truck.class), any(Parcel.class))).thenReturn(true);
        doNothing().when(truckService).placeParcelInTruck(any(Truck.class), any(Parcel.class));
        when(parcelMapper.parcelToString(any(Parcel.class))).thenReturn("mockParcel");

        List<Truck> result = easyLoadStrategy.loadParcels(parcels, trucks);

        assertThat(result).hasSize(3);
        assertThat(parcels.get(0).getIsLoaded()).isTrue();
        assertThat(parcels.get(1).getIsLoaded()).isTrue();
        assertThat(parcels.get(2).getIsLoaded()).isTrue();
    }

    @Test
    void loadParcels_shouldThrowException_whenParcelsAndTrucksCountMismatch() {
        List<Truck> fewerTrucks = List.of(new Truck(), new Truck());

        assertThatThrownBy(() -> easyLoadStrategy.loadParcels(parcels, fewerTrucks))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Parcels and trucks must have the same count");
    }

    @Test
    void loadParcels_shouldThrowException_whenParcelCannotBeLoaded() {
        when(truckService.tryPlacePackageInTruck(any(Truck.class), any(Parcel.class))).thenReturn(false);

        assertThatThrownBy(() -> easyLoadStrategy.loadParcels(parcels, trucks))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Parcel Parcel1 was not loaded in truck");
    }

    @Test
    void loadParcels_shouldHandleEmptyTrucksList() {
        List<Truck> emptyTrucks = new ArrayList<>();
        when(truckService.tryPlacePackageInTruck(any(Truck.class), any(Parcel.class))).thenReturn(true);

        List<Truck> result = easyLoadStrategy.loadParcels(parcels, emptyTrucks);

        assertThat(result).hasSize(3);
    }

    @Test
    void loadParcels_shouldLoadParcelsIntoCreatedTrucks_whenTrucksListIsEmpty() {
        when(truckService.tryPlacePackageInTruck(any(Truck.class), any(Parcel.class))).thenReturn(true);
        doNothing().when(truckService).placeParcelInTruck(any(Truck.class), any(Parcel.class));
        when(parcelMapper.parcelToString(any(Parcel.class))).thenReturn("mockParcel");

        List<Truck> result = easyLoadStrategy.loadParcels(parcels, new ArrayList<>());

        assertThat(result).hasSize(3);
        assertThat(parcels.get(0).getIsLoaded()).isTrue();
        assertThat(parcels.get(1).getIsLoaded()).isTrue();
        assertThat(parcels.get(2).getIsLoaded()).isTrue();
    }
}
