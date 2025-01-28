package ru.hoff.edu.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ru.hoff.edu.domain.Parcel;
import ru.hoff.edu.domain.Truck;
import ru.hoff.edu.entity.ParcelEntity;
import ru.hoff.edu.repository.ParcelRepository;
import ru.hoff.edu.service.exception.ParcelNotFoundException;
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

class ParcelServiceTest {

    @Mock
    private ParcelRepository parcelRepository;

    private ParcelMapper mapper = new ParcelMapper();

    @Mock
    private ParcelValidator parcelValidator;

    private ParcelService parcelService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        parcelService = new ParcelService(parcelRepository, mapper, parcelValidator);
    }

    @Test
    void add_ShouldAddParcel() {
        ParcelEntity parcel = new ParcelEntity("Parcel1", "A", "A", false);
        ParcelMapper parcelMapper = new ParcelMapper();

        when(parcelRepository.findById("Parcel1")).thenReturn(Optional.empty());

        try (MockedStatic<ParcelValidator> mockedValidator = Mockito.mockStatic(ParcelValidator.class)) {
            mockedValidator.when(() -> parcelValidator.isParcelFormValid(any(char[][].class), anyChar()))
                    .thenReturn(true);

            parcelService.add(parcelMapper.fromEntity(parcel));

            verify(parcelRepository, times(1)).save(any(ParcelEntity.class));

            mockedValidator.verify(() -> parcelValidator.isParcelFormValid(any(char[][].class), anyChar()));
        }
    }

    @Test
    void add_ShouldThrowException_WhenParcelExists() {
        ParcelEntity parcel = new ParcelEntity("Parcel1", "A", "A", false);
        ParcelMapper parcelMapper = new ParcelMapper();
        when(parcelRepository.findById("Parcel1")).thenReturn(Optional.of(parcel));

        assertThrows(IllegalArgumentException.class, () -> parcelService.add(parcelMapper.fromEntity(parcel)));
    }

    @Test
    void add_ShouldThrowsException_WhenFormInvalid() {
        ParcelEntity parcel = new ParcelEntity("Parcel1", "A", "A", false);

        when(parcelRepository.findById("Parcel1")).thenReturn(Optional.empty());

        try (MockedStatic<ParcelValidator> mockedValidator = mockStatic(ParcelValidator.class)) {
            mockedValidator.when(() -> parcelValidator.isParcelFormValid(any(char[][].class), anyChar()))
                    .thenReturn(false);

            assertThrows(IllegalArgumentException.class, () -> parcelService.add(mapper.fromEntity(parcel)));

            mockedValidator.verify(() -> parcelValidator.isParcelFormValid(any(char[][].class), anyChar()));
        }
    }

    @Test
    void findAll_ShouldReturnAllParcels() {
        ParcelEntity parcel1 = new ParcelEntity("Parcel1", "A", "A", false);
        ParcelEntity parcel2 = new ParcelEntity("Parcel2", "A", "B", false);
        List<ParcelEntity> parcelEntities = Arrays.asList(parcel1, parcel2);

        Page<ParcelEntity> parcelEntityPage = new PageImpl<>(parcelEntities, PageRequest.of(0, 2), parcelEntities.size());

        when(parcelRepository.findAll(any(Pageable.class))).thenReturn(parcelEntityPage);

        Page<Parcel> parcels = parcelService.findAll(0, 2);

        assertEquals(2, parcels.getTotalElements());
        assertEquals(2, parcels.getContent().size());
        verify(parcelRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void findByName_ShouldFindParcelByName() {
        ParcelEntity parcel = new ParcelEntity("Parcel1", "A", "A", false);
        when(parcelRepository.findById("Parcel1")).thenReturn(Optional.of(parcel));

        Parcel foundParcel = parcelService.findByName("Parcel1");

        assertNotNull(foundParcel);
        assertEquals("Parcel1", foundParcel.getName());
        verify(parcelRepository, times(1)).findById("Parcel1");
    }

    @Test
    void deleteParcel_ShouldDeleteParcel() {
        parcelService.delete("Parcel1");

        verify(parcelRepository, times(1)).deleteById("Parcel1");
    }

    @Test
    void edit_ShouldThrowsException_WhenParcelNotFound() {
        when(parcelRepository.findById("Parcel1")).thenReturn(Optional.empty());

        assertThrows(ParcelNotFoundException.class, () -> parcelService.edit("Parcel1", "NewParcel", new char[][]{{'B'}}, "B"));
    }

    @Test
    void edit_ShouldThrowsException_WhenFormInvalid() {
        ParcelEntity parcel = new ParcelEntity("Parcel1", "A", "A", false);

        when(parcelRepository.findById("Parcel1")).thenReturn(Optional.of(parcel));

        try (MockedStatic<ParcelValidator> mockedValidator = Mockito.mockStatic(ParcelValidator.class)) {
            mockedValidator.when(() -> parcelValidator.isParcelFormValid(any(char[][].class), anyChar()))
                    .thenReturn(false);

            assertThrows(IllegalArgumentException.class, () ->
                    parcelService.edit("Parcel1", "NewParcel", new char[][]{{'B'}}, "B")
            );

            mockedValidator.verify(() -> parcelValidator.isParcelFormValid(any(char[][].class), anyChar()));
        }
    }

    @Test
    void findSuitableTruck_ShouldReturnSuitableTruck_ForPlaceParcel() {
        Parcel parcel = new Parcel("Parcel1", new char[][]{{'A'}}, "A", false);
        Truck truck = new Truck();

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
