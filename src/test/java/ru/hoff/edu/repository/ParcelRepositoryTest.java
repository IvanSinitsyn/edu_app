package ru.hoff.edu.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.hoff.edu.model.entity.ParcelEntity;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ParcelRepositoryTest {

    @Autowired
    private ParcelRepository parcelRepository;

    @BeforeEach
    void setUp() {
        parcelRepository.saveAll(List.of(
                new ParcelEntity("1", "1", "1", false),
                new ParcelEntity("2", "22", "2", false),
                new ParcelEntity("3", "333", "3", false),
                new ParcelEntity("4", "4444", "4", false),
                new ParcelEntity("5", "55555", "5", false),
                new ParcelEntity("6", "666\n666\n666", "6", false),
                new ParcelEntity("7", "7777\n777", "7", false),
                new ParcelEntity("8", "8888\n8888", "8", false),
                new ParcelEntity("9", "999\n999\n999", "9", false)
        ));
    }

    @Test
    void initializeParcels_shouldContainInitialParcels() {
        List<ParcelEntity> parcels = parcelRepository.findAll();

        assertEquals(9, parcels.size());
        assertTrue(parcels.stream().anyMatch(parcel -> parcel.getName().equals("1")));
        assertTrue(parcels.stream().anyMatch(parcel -> parcel.getName().equals("5")));
        assertTrue(parcels.stream().anyMatch(parcel -> parcel.getName().equals("9")));
    }

    @Test
    void addParcel_shouldAddParcelToRepository() {
        ParcelEntity newParcel = new ParcelEntity(
                "TestParcel",
                "TE\nST",
                "T",
                false
        );

        parcelRepository.save(newParcel);
        List<ParcelEntity> parcels = parcelRepository.findAll();

        assertEquals(10, parcels.size());
        assertTrue(parcels.stream().anyMatch(p -> p.getName().equals("TestParcel")));
    }

    @Test
    void edit_shouldReplaceParcelWithNewOne() {
        Optional<ParcelEntity> parcel = parcelRepository.findById("3");
        parcel.get().setName("UpdatedParcel");
        parcel.get().setForm("XY\nZ ");
        ParcelEntity parcelEntity = parcelRepository.save(parcel.get());

        assertThat(parcelEntity).isNotNull();
        assertEquals("XY\nZ ", parcelEntity.getForm());
    }

    @Test
    void deleteParcel_shouldRemoveParcelFromRepository() {
        ParcelEntity parcel = parcelRepository.findById("4").orElse(null);
        assertThat(parcel).isNotNull();

        parcelRepository.delete(parcel);
        List<ParcelEntity> parcels = parcelRepository.findAll();

        assertEquals(8, parcels.size());
        assertFalse(parcels.stream().anyMatch(p -> p.getName().equals("4")));
    }

    @Test
    void findAll_shouldReturnAllParcels() {
        List<ParcelEntity> parcels = parcelRepository.findAll();
        assertEquals(9, parcels.size());
    }

    @Test
    void findParcelByName_shouldReturnParcel_whenParcelExists() {
        ParcelEntity parcel = parcelRepository.findById("6").orElse(null);
        assertThat(parcel).isNotNull();
        assertEquals("6", parcel.getName());
    }

    @Test
    void findParcelByName_shouldReturnEmpty_whenParcelDoesNotExist() {
        String parcelName = "NonExistentParcel";

        ParcelEntity foundParcel = parcelRepository.findById(parcelName).orElse(null);

        assertThat(foundParcel).isNull();
    }
}

