package ru.hoff.edu.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import ru.hoff.edu.domain.Parcel;
import ru.hoff.edu.domain.Truck;
import ru.hoff.edu.repository.ParcelRepository;
import ru.hoff.edu.validation.ParcelValidator;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyChar;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ParcelServiceTest {

    @Mock
    private ParcelRepository parcelRepository;

    @InjectMocks
    private ParcelService parcelService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void add_ShouldAddParcel() {
        Parcel parcel = new Parcel("Parcel1", new char[][]{{'A'}}, "A", false);

        when(parcelRepository.findParcelByName("Parcel1")).thenReturn(Optional.empty());

        try (MockedStatic<ParcelValidator> mockedValidator = Mockito.mockStatic(ParcelValidator.class)) {
            mockedValidator.when(() -> ParcelValidator.isParcelFormValid(any(char[][].class), anyChar()))
                    .thenReturn(true);

            parcelService.add(parcel);

            verify(parcelRepository, times(1)).addParcel(parcel);

            mockedValidator.verify(() -> ParcelValidator.isParcelFormValid(any(char[][].class), anyChar()));
        }
    }

    @Test
    void add_ShouldThrowException_WhenParcelExists() {
        Parcel parcel = new Parcel("Parcel1", new char[][]{{'A'}}, "A", false);
        when(parcelRepository.findParcelByName("Parcel1")).thenReturn(Optional.of(parcel));

        assertThrows(IllegalArgumentException.class, () -> parcelService.add(parcel));
    }

    @Test
    void add_ShouldThrowsException_WhenFormInvalid() {
        Parcel parcel = new Parcel("Parcel1", new char[][]{{'A'}}, "A", false);

        when(parcelRepository.findParcelByName("Parcel1")).thenReturn(Optional.empty());

        try (MockedStatic<ParcelValidator> mockedValidator = mockStatic(ParcelValidator.class)) {
            mockedValidator.when(() -> ParcelValidator.isParcelFormValid(any(char[][].class), anyChar()))
                    .thenReturn(false);

            assertThrows(IllegalArgumentException.class, () -> parcelService.add(parcel));

            mockedValidator.verify(() -> ParcelValidator.isParcelFormValid(any(char[][].class), anyChar()));
        }
    }

    @Test
    void findAll_ShouldReturnAllParcels() {
        Parcel parcel1 = new Parcel("Parcel1", new char[][]{{'A'}}, "A", false);
        Parcel parcel2 = new Parcel("Parcel2", new char[][]{{'B'}}, "B", false);
        when(parcelRepository.findAllParcels()).thenReturn(Arrays.asList(parcel1, parcel2));

        List<Parcel> parcels = parcelService.findAll();

        assertEquals(2, parcels.size());
        verify(parcelRepository, times(1)).findAllParcels();
    }

    @Test
    void findByName_ShouldFindParcelByName() {
        Parcel parcel = new Parcel("Parcel1", new char[][]{{'A'}}, "A", false);
        when(parcelRepository.findParcelByName("Parcel1")).thenReturn(Optional.of(parcel));

        Optional<Parcel> foundParcel = parcelService.findByName("Parcel1");

        assertTrue(foundParcel.isPresent());
        assertEquals("Parcel1", foundParcel.get().getName());
        verify(parcelRepository, times(1)).findParcelByName("Parcel1");
    }

    @Test
    void deleteParcel_ShouldDeleteParcel() {
        parcelService.delete("Parcel1");

        verify(parcelRepository, times(1)).deleteParcel("Parcel1");
    }

    @Test
    void edit_ShouldThrowsException_WhenParcelNotFound() {
        when(parcelRepository.findParcelByName("Parcel1")).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> parcelService.edit("Parcel1", "NewParcel", new char[][]{{'B'}}, "B"));
    }

    @Test
    void edit_ShouldThrowsException_WhenFormInvalid() {
        Parcel parcel = new Parcel("Parcel1", new char[][]{{'A'}}, "A", false);

        when(parcelRepository.findParcelByName("Parcel1")).thenReturn(Optional.of(parcel));

        try (MockedStatic<ParcelValidator> mockedValidator = Mockito.mockStatic(ParcelValidator.class)) {
            mockedValidator.when(() -> ParcelValidator.isParcelFormValid(any(char[][].class), anyChar()))
                    .thenReturn(false);

            assertThrows(IllegalArgumentException.class, () ->
                    parcelService.edit("Parcel1", "NewParcel", new char[][]{{'B'}}, "B")
            );

            mockedValidator.verify(() -> ParcelValidator.isParcelFormValid(any(char[][].class), anyChar()));
        }
    }

    @Test
    void findSuitableTruck_ShouldReturnSuitableTruck_ForPlaceParcel() {
        Parcel parcel = new Parcel("Parcel1", new char[][]{{'A'}}, "A", false);
        Truck truck = new Truck();

        ParcelService parcelService = new ParcelService(parcelRepository);

        Truck suitableTruck = parcelService.findSuitableTruck(Collections.singletonList(truck), parcel);

        assertNotNull(suitableTruck);
        assertEquals(truck, suitableTruck);
    }

    @Test
    void tryPlacePackageInTruck_ShouldReturnTrue_WhenParcelCanBePlaced() {
        Parcel parcel = new Parcel("Parcel1", new char[][]{{'A'}}, "A", false);
        Truck truck = new Truck();

        boolean result = parcelService.tryPlacePackageInTruck(truck, parcel);

        assertTrue(result);
    }

    @Test
    void placeParcelInTruck_ShouldPlaceParcelInTruck() {
        Parcel parcel = new Parcel("Parcel1", new char[][]{{'A'}}, "A", false);
        Truck truck = new Truck();

        parcelService.placeParcelInTruck(truck, parcel);

        assertFalse(truck.isEmpty());
    }
}
