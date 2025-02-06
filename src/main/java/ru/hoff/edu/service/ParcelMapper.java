package ru.hoff.edu.service;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.hoff.edu.domain.Parcel;
import ru.hoff.edu.model.dto.ParcelDto;
import ru.hoff.edu.model.entity.ParcelEntity;

import java.util.Arrays;
import java.util.List;


@Mapper(componentModel = "spring", uses = {DataConverter.class})
public interface ParcelMapper {

    @Mapping(target = "form", source = "form", qualifiedByName = "convertArrayToString")
    ParcelEntity toEntity(Parcel parcel);

    @Mapping(target = "form", source = "form", qualifiedByName = "convertStringToForm")
    Parcel fromEntity(ParcelEntity parcelEntity);

    @Mapping(target = "name", source = "name")
    @Mapping(target = "symbol", source = "symbol")
    @Mapping(target = "isLoaded", source = "isLoaded")
    @Mapping(target = "form", expression = "java(cloneCharMatrix(parcel.getForm()))")
    ParcelDto toDto(Parcel parcel);

    @Mapping(target = "form", expression = "java(cloneCharMatrix(parcelDto.form()))")
    Parcel toParcel(ParcelDto parcelDto);

    List<ParcelDto> toDtoList(List<Parcel> parcels);

    default char[][] cloneCharMatrix(char[][] original) {
        if (original == null) return null;
        return Arrays.stream(original)
                .map(char[]::clone)
                .toArray(char[][]::new);
    }
}

