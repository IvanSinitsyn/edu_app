package ru.hoff.edu.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.hoff.edu.domain.Parcel;
import ru.hoff.edu.model.dto.ParcelDto;
import ru.hoff.edu.model.entity.ParcelEntity;
import ru.hoff.edu.service.mapper.ParcelMapper;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ParcelMapperTest {

    private ParcelMapper parcelMapper;

    @BeforeEach
    void setUp() {
        parcelMapper = Mappers.getMapper(ParcelMapper.class);
    }

    @Test
    void testToEntity() {
        Parcel parcel = new Parcel("testParcel", new char[][]{{'X', ' '}}, "X", true);
        ParcelEntity entity = parcelMapper.toEntity(parcel);

        assertThat(entity).isNotNull();
        assertThat(entity.getName()).isEqualTo(parcel.getName());
        assertThat(entity.getSymbol()).isEqualTo(parcel.getSymbol());
        assertThat(entity.isLoaded()).isEqualTo(parcel.getIsLoaded());
        assertThat(entity.getForm()).isEqualTo(parcelMapper.convertArrayToString(parcel.getForm()));
    }

    @Test
    void testFromEntity() {
        ParcelEntity entity = new ParcelEntity("testParcel", "X ", "X", true);
        Parcel parcel = parcelMapper.fromEntity(entity);

        assertThat(parcel).isNotNull();
        assertThat(parcel.getName()).isEqualTo(entity.getName());
        assertThat(parcel.getSymbol()).isEqualTo(entity.getSymbol());
        assertThat(parcel.getIsLoaded()).isEqualTo(entity.isLoaded());
        assertThat(parcel.getForm()).isDeepEqualTo(parcelMapper.convertStringToForm(entity.getForm()));
    }

    @Test
    void testToDto() {
        Parcel parcel = new Parcel("testParcel", new char[][]{{'X', ' '}}, "X", true);
        ParcelDto dto = parcelMapper.toDto(parcel);

        assertThat(dto).isNotNull();
        assertThat(dto.name()).isEqualTo(parcel.getName());
        assertThat(dto.symbol()).isEqualTo(parcel.getSymbol());
        assertThat(dto.isLoaded()).isEqualTo(parcel.getIsLoaded());
        assertThat(dto.form()).isDeepEqualTo(parcel.getForm());
    }

    @Test
    void testToDtoList() {
        List<Parcel> parcels = List.of(
                new Parcel("parcel1", new char[][]{{'A'}}, "A", false),
                new Parcel("parcel2", new char[][]{{'B'}}, "B", true)
        );

        List<ParcelDto> dtos = parcelMapper.toDtoList(parcels);

        assertThat(dtos).isNotNull().hasSize(2);
        assertThat(dtos.get(0).name()).isEqualTo("parcel1");
        assertThat(dtos.get(1).isLoaded()).isTrue();
    }

    @Test
    void testCloneCharMatrix() {
        char[][] original = {{'X', 'O'}, {'O', 'X'}};
        char[][] cloned = parcelMapper.cloneCharMatrix(original);

        assertThat(cloned).isNotSameAs(original).isDeepEqualTo(original);
    }

    @Test
    void testParcelToString() {
        Parcel parcel = new Parcel("test", new char[][]{{'X', 'O'}, {'O', 'X'}}, "X", true);
        String expected = "|XO|" + System.lineSeparator() + "|OX|";
        String result = parcelMapper.parcelToString(parcel);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void testConvertArrayToString() {
        char[][] array = {{'X', 'O'}, {'O', 'X'}};
        String result = parcelMapper.convertArrayToString(array);

        assertThat(result).isEqualTo("XO\nOX");
    }

    @Test
    void testConvertStringToForm() {
        String input = "XO\nOX";
        char[][] result = parcelMapper.convertStringToForm(input);

        assertThat(result).isDeepEqualTo(new char[][]{{'X', 'O'}, {'O', 'X'}});
    }
}
