package ru.hoff.edu.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.hoff.edu.domain.Parcel;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ParcelRepositoryTest {

    private ParcelRepository parcelRepository;

    @BeforeEach
    void setUp() {
        parcelRepository = new ParcelRepository();
    }

    @Test
    void initializeParcels_shouldContainInitialParcels() {
        // Act
        List<Parcel> parcels = parcelRepository.findAllParcels();

        // Assert
        assertEquals(9, parcels.size());
        assertTrue(parcels.stream().anyMatch(parcel -> parcel.getName().equals("1")));
        assertTrue(parcels.stream().anyMatch(parcel -> parcel.getName().equals("5")));
        assertTrue(parcels.stream().anyMatch(parcel -> parcel.getName().equals("9")));
    }

    @Test
    void addParcel_shouldAddParcelToRepository() {
        // Arrange
        Parcel newParcel = new Parcel(
                "TestParcel",
                new char[][]{{'T', 'E'}, {'S', 'T'}},
                "T",
                false
        );

        // Act
        parcelRepository.addParcel(newParcel);
        List<Parcel> parcels = parcelRepository.findAllParcels();

        // Assert
        assertEquals(10, parcels.size());
        assertTrue(parcels.contains(newParcel));
    }

    @Test
    void edit_shouldReplaceParcelWithNewOne() {
        // Arrange
        String parcelId = "3";
        char[][] newForm = {{'N', 'E', 'W'}, {'F', 'O', 'R'}, {'M', '3', ' '}};
        String newName = "NewParcel";
        String newSymbol = "N";

        // Act
        Parcel updatedParcel = parcelRepository.edit(parcelId, newName, newForm, newSymbol);

        // Assert
        List<Parcel> parcels = parcelRepository.findAllParcels();
        assertEquals(9, parcels.size()); // Количество должно оставаться прежним
        assertFalse(parcels.stream().anyMatch(parcel -> parcel.getName().equals(parcelId)));
        assertTrue(parcels.contains(updatedParcel));
        assertEquals("NewParcel", updatedParcel.getName());
        assertArrayEquals(newForm, updatedParcel.getForm());
    }

    @Test
    void deleteParcel_shouldRemoveParcelFromRepository() {
        // Arrange
        String parcelName = "4";

        // Act
        parcelRepository.deleteParcel(parcelName);
        List<Parcel> parcels = parcelRepository.findAllParcels();

        // Assert
        assertEquals(8, parcels.size());
        assertFalse(parcels.stream().anyMatch(parcel -> parcel.getName().equals(parcelName)));
    }

    @Test
    void findAllParcels_shouldReturnAllParcels() {
        // Act
        List<Parcel> parcels = parcelRepository.findAllParcels();

        // Assert
        assertEquals(9, parcels.size());
    }

    @Test
    void findParcelByName_shouldReturnParcel_whenParcelExists() {
        // Arrange
        String parcelName = "6";

        // Act
        Optional<Parcel> foundParcel = parcelRepository.findParcelByName(parcelName);

        // Assert
        assertTrue(foundParcel.isPresent());
        assertEquals(parcelName, foundParcel.get().getName());
    }

    @Test
    void findParcelByName_shouldReturnEmpty_whenParcelDoesNotExist() {
        // Arrange
        String parcelName = "NonExistentParcel";

        // Act
        Optional<Parcel> foundParcel = parcelRepository.findParcelByName(parcelName);

        // Assert
        assertFalse(foundParcel.isPresent());
    }
}

