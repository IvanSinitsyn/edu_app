package ru.hoff.edu.service.strategy.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.hoff.edu.domain.Parcel;
import ru.hoff.edu.domain.Truck;
import ru.hoff.edu.service.core.ParcelService;
import ru.hoff.edu.service.mapper.ParcelMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class OptimalLoadStrategyTest {

    @Mock
    private ParcelService parcelService;

    @Mock
    private ParcelMapper parcelMapper;

    @Mock
    private Truck truck;

    @Mock
    private Parcel parcel;

    private OptimalLoadStrategy optimalLoadStrategy;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        optimalLoadStrategy = new OptimalLoadStrategy(parcelService, parcelMapper);
    }

    @Test
    void loadParcels_shouldLoadParcelsIntoExistingTrucks() {
        List<Parcel> parcels = Arrays.asList(parcel, parcel);
        List<Truck> trucks = new ArrayList<>(List.of(truck));

        when(parcel.getIsLoaded()).thenReturn(false);
        when(parcelMapper.parcelToString(parcel)).thenReturn("Parcel1");
        when(parcelService.tryPlacePackageInTruck(truck, parcel)).thenReturn(true);
        doNothing().when(parcelService).placeParcelInTruck(any(Truck.class), any(Parcel.class));

        List<Truck> result = optimalLoadStrategy.loadParcels(parcels, trucks);

        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(1);
        verify(parcelService, times(2)).placeParcelInTruck(any(Truck.class), any(Parcel.class));
    }

    @Test
    void loadParcels_shouldCreateNewTrucksIfNoneProvided() {
        List<Parcel> parcels = Arrays.asList(parcel, parcel);

        when(parcel.getIsLoaded()).thenReturn(false);
        when(parcelMapper.parcelToString(parcel)).thenReturn("Parcel1");
        when(parcelService.tryPlacePackageInTruck(any(Truck.class), any(Parcel.class)))
                .thenReturn(true);
        doNothing().when(parcelService).placeParcelInTruck(any(Truck.class), any(Parcel.class));

        List<Truck> result = optimalLoadStrategy.loadParcels(parcels, null);

        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(1);
        verify(parcelService, times(2)).placeParcelInTruck(any(Truck.class), any(Parcel.class));
    }

    @Test
    void loadParcels_shouldThrowExceptionWhenParcelCannotBeLoaded() {
        List<Parcel> parcels = List.of(parcel);
        List<Truck> trucks = List.of(truck);

        when(parcel.getIsLoaded()).thenReturn(false);
        when(parcelMapper.parcelToString(parcel)).thenReturn("Parcel1");
        when(parcelService.tryPlacePackageInTruck(any(Truck.class), any(Parcel.class)))
                .thenReturn(false);
        when(truck.showTruckSize()).thenReturn("TruckSize1");

        assertThatThrownBy(() -> optimalLoadStrategy.loadParcels(parcels, trucks))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Unable to load package Parcel1 into truck TruckSize1");
    }
}
