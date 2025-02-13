package ru.hoff.edu.service.mapper;

import org.mapstruct.Mapper;
import ru.hoff.edu.domain.Parcel;
import ru.hoff.edu.model.dto.ParcelDto;
import ru.hoff.edu.model.entity.ParcelEntity;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ParcelMapper {

    default ParcelEntity toEntity(Parcel parcel) {
        ParcelEntity parcelEntity = new ParcelEntity();
        parcelEntity.setForm(convertArrayToString(parcel.getForm()));
        parcelEntity.setName(parcel.getName());
        parcelEntity.setSymbol(parcel.getSymbol());
        parcelEntity.setLoaded(parcel.getIsLoaded());
        return parcelEntity;
    }

    default Parcel fromEntity(ParcelEntity parcelEntity) {
        Parcel parcel = new Parcel();
        parcel.setName(parcelEntity.getName());
        parcel.setSymbol(parcelEntity.getSymbol());
        parcel.setIsLoaded(parcelEntity.isLoaded());
        parcel.setForm(convertStringToForm(parcelEntity.getForm()));
        return parcel;
    }

    default ParcelDto toDto(Parcel parcel) {
        return ParcelDto.builder()
                .name(parcel.getName())
                .symbol(parcel.getSymbol())
                .isLoaded(parcel.getIsLoaded())
                .form(cloneCharMatrix(parcel.getForm()))
                .build();
    }

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