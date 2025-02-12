package ru.hoff.edu.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.hoff.edu.model.dto.request.LoadParcelRequestDto;
import ru.hoff.edu.service.mediator.request.impl.LoadParcelsRequest;

import java.util.Arrays;
import java.util.List;

@Mapper
public interface LoadParcelRequestMapper {

    @Mapping(target = "parcelIds", expression = "java(stringToList(dto.parcelIds(), \",\"))")
    @Mapping(target = "trucksDescriptions", expression = "java(stringToList(dto.trucksDescriptions(), \"\\\\n\"))")
    LoadParcelsRequest toRequest(LoadParcelRequestDto dto);

    default List<String> stringToList(String str, String delimiter) {
        return Arrays.stream(str.split(delimiter)).toList();
    }
}
