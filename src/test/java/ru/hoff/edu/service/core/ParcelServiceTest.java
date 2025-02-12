package ru.hoff.edu.service.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ru.hoff.edu.domain.Parcel;
import ru.hoff.edu.domain.Truck;
import ru.hoff.edu.model.entity.ParcelEntity;
import ru.hoff.edu.repository.ParcelRepository;
import ru.hoff.edu.service.exception.ParcelNotFoundException;
import ru.hoff.edu.service.mapper.ParcelMapper;
import ru.hoff.edu.validation.ParcelValidator;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ParcelServiceTest {

    @Mock
    private ParcelRepository parcelRepository;

    private final ParcelMapper parcelMapper = Mappers.getMapper(ParcelMapper.class);

    private ParcelService parcelService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ParcelValidator parcelValidator = new ParcelValidator();
        parcelService = new ParcelService(parcelRepository, parcelMapper, parcelValidator);
    }

    @Test
    void add_ShouldAddParcel() {
        char[][] form = new char[][]{{'A', 'A'}, {'A', 'A'}};

        Parcel parcel = new Parcel("Parcel1", form, "A", false);

        when(parcelRepository.existsById(parcel.getName())).thenReturn(false);

        parcelService.add(parcel);

        verify(parcelRepository, times(1)).save(any());
    }

    @Test
    void add_ShouldThrowException_WhenParcelExists() {
        ParcelEntity parcel = new ParcelEntity("Parcel1", "A", "A", false);
        when(parcelRepository.existsById(parcel.getName())).thenReturn(true);
        assertThatThrownBy(() -> parcelService.add(parcelMapper.fromEntity(parcel))).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void add_ShouldThrowException_WhenFormInvalid() {
        char[][] form = new char[][]{{'A', ' '}, {' ', 'A'}};

        Parcel parcel = new Parcel("Parcel1", form, "A", false);

        when(parcelRepository.existsById(anyString())).thenReturn(false);

        assertThatThrownBy(() -> parcelService.add(parcel))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Форма посылки невалидная");
    }

    @Test
    void findAll_ShouldReturnAllParcels() {
        ParcelEntity parcel1 = new ParcelEntity("Parcel1", "A", "A", false);
        ParcelEntity parcel2 = new ParcelEntity("Parcel2", "B", "B", false);
        List<ParcelEntity> parcelEntities = Arrays.asList(parcel1, parcel2);

        Page<ParcelEntity> parcelEntityPage = new PageImpl<>(parcelEntities, PageRequest.of(0, 2), parcelEntities.size());

        when(parcelRepository.findAll(any(Pageable.class))).thenReturn(parcelEntityPage);

        Page<Parcel> parcels = parcelService.findAll(0, 2);

        assertThat(parcels.getTotalElements()).isEqualTo(2);
        assertThat(parcels.getContent().size()).isEqualTo(2);
        verify(parcelRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void findByName_ShouldFindParcelByName() {
        ParcelEntity parcel = new ParcelEntity("Parcel1", "A", "A", false);
        when(parcelRepository.findById("Parcel1")).thenReturn(Optional.of(parcel));

        Parcel foundParcel = parcelService.findByName("Parcel1");

        assertThat(foundParcel).isNotNull();
        assertThat(foundParcel.getName()).isEqualTo("Parcel1");
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

        assertThatThrownBy(() -> parcelService.edit("Parcel1", "NewParcel", new char[][]{{'B'}}, "B")).isInstanceOf(ParcelNotFoundException.class);
    }

    @Test
    void edit_ShouldThrowException_WhenFormInvalid() {
        ParcelEntity parcel = new ParcelEntity("Parcel1", "A", "A", false);

        when(parcelRepository.findById("Parcel1")).thenReturn(Optional.of(parcel));

        assertThatThrownBy(() ->
                parcelService.edit("Parcel1", "NewParcel", new char[][]{{'A', ' '}, {' ', 'A'}}, "A"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void findSuitableTruck_ShouldReturnSuitableTruck_ForPlaceParcel() {
        Parcel parcel = new Parcel("Parcel1", new char[][]{{'A'}}, "A", false);
        Truck truck = new Truck();

        Truck suitableTruck = parcelService.findSuitableTruck(Collections.singletonList(truck), parcel);

        assertThat(suitableTruck).isNotNull();
        assertThat(suitableTruck).isEqualTo(truck);
    }

    @Test
    void tryPlacePackageInTruck_ShouldReturnTrue_WhenParcelCanBePlaced() {
        Parcel parcel = new Parcel("Parcel1", new char[][]{{'A'}}, "A", false);
        Truck truck = new Truck();

        boolean result = parcelService.tryPlacePackageInTruck(truck, parcel);

        assertThat(result).isTrue();
    }

    @Test
    void placeParcelInTruck_ShouldPlaceParcelInTruck() {
        Parcel parcel = new Parcel("Parcel1", new char[][]{{'A'}}, "A", false);
        Truck truck = new Truck();

        parcelService.placeParcelInTruck(truck, parcel);

        assertThat(truck.isEmpty()).isFalse();
    }


}
