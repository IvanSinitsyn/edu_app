package ru.hoff.edu.service.strategy.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.hoff.edu.domain.Parcel;
import ru.hoff.edu.domain.Truck;
import ru.hoff.edu.service.core.TruckService;
import ru.hoff.edu.service.mapper.ParcelMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class EqualLoadStrategyTest {

    @Mock
    private TruckService truckService;

    @Mock
    private ParcelMapper parcelMapper;

    @Mock
    private Truck truck;

    @Mock
    private Parcel parcel;

    private EqualLoadStrategy equalLoadStrategy;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        equalLoadStrategy = new EqualLoadStrategy(parcelMapper, truckService);
    }

    @Test
    void loadParcels_shouldLoadParcelsIntoExistingTrucks() {
        List<Parcel> parcels = Arrays.asList(parcel, parcel);
        List<Truck> trucks = new ArrayList<>();
        trucks.add(truck);

        when(parcelMapper.parcelToString(parcel)).thenReturn("Parcel1");
        when(truckService.findSuitableTruck(trucks, parcel)).thenReturn(Optional.of(truck));
        doNothing().when(truckService).placeParcelInTruck(any(Truck.class), any(Parcel.class));

        List<Truck> result = equalLoadStrategy.loadParcels(parcels, trucks);

        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(1);
        verify(truckService, times(2)).placeParcelInTruck(any(Truck.class), any(Parcel.class));
    }

    @Test
    void loadParcels_shouldCreateNewTrucksIfNoneProvided() {
        List<Parcel> parcels = Arrays.asList(parcel, parcel);

        when(parcelMapper.parcelToString(parcel)).thenReturn("Parcel1");
        when(truckService.findSuitableTruck(anyList(), any(Parcel.class))).thenReturn(null);
        doNothing().when(truckService).placeParcelInTruck(any(Truck.class), any(Parcel.class));

        List<Truck> result = equalLoadStrategy.loadParcels(parcels, null);

        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(2);
        verify(truckService, times(2)).placeParcelInTruck(any(Truck.class), any(Parcel.class));
    }

    @Test
    void loadParcels_shouldThrowExceptionWhenNoSuitableTruckFound() {
        List<Parcel> parcels = Arrays.asList(parcel);
        List<Truck> trucks = Arrays.asList(truck);

        when(parcelMapper.parcelToString(parcel)).thenReturn("Parcel1");
        when(truckService.findSuitableTruck(trucks, parcel)).thenReturn(null);

        assertThatThrownBy(() -> equalLoadStrategy.loadParcels(parcels, trucks))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Cannot find suitable truck for parcel: Parcel1");
    }
}
