package ru.hoff.edu.service;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.hoff.edu.domain.Parcel;
import ru.hoff.edu.model.dto.ParcelDto;
import ru.hoff.edu.model.entity.ParcelEntity;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = ParcelMapper.class)
public interface ParcelMapper {

    @Mapping(target = "form", source = "form")
    ParcelEntity toEntity(Parcel parcel);

    @Mapping(target = "form", source = "form")
    Parcel fromEntity(ParcelEntity parcelEntity);

    @Mapping(target = "name", source = "name")
    @Mapping(target = "symbol", source = "symbol")
    @Mapping(target = "isLoaded", source = "isLoaded")
    @Mapping(target = "form", expression = "java(cloneCharMatrix(parcel.getForm()))")
    ParcelDto toDto(Parcel parcel);

    List<ParcelDto> toDtoList(List<Parcel> parcels);

    default char[][] cloneCharMatrix(char[][] original) {
        if (original == null) return null;
        return Arrays.stream(original)
                .map(char[]::clone)
                .toArray(char[][]::new);
    }

    default String parcelToString(Parcel parcel) {
        char[][] parcelShape = parcel.getForm();
        StringBuilder sb = new StringBuilder();
        for (char[] chars : parcelShape) {
            sb.append("|");
            for (char aChar : chars) {
                sb.append(aChar);
            }
            sb.append("|").append(System.lineSeparator());
        }
        return sb.toString().trim();
    }

    default String convertArrayToString(char[][] shape) {
        if (shape == null) return "";
        return Arrays.stream(shape)
                .map(String::new)
                .collect(Collectors.joining("\n"));
    }

    default char[][] convertStringToForm(String input) {
        if (input == null || input.isEmpty()) {
            return new char[][]{};
        }

        String[] rows = input.split("\\r?\\n");
        int numCols = Arrays.stream(rows).mapToInt(String::length).max().orElse(0);
        char[][] charArray = new char[rows.length][numCols];

        for (int i = 0; i < rows.length; i++) {
            Arrays.fill(charArray[i], ' '); // Заполняем пробелами, чтобы избежать пустых мест
            for (int j = 0; j < rows[i].length(); j++) {
                charArray[i][j] = rows[i].charAt(j);
            }
        }
        return charArray;
    }
}