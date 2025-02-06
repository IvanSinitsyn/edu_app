package ru.hoff.edu.model.dto.request;

import ru.hoff.edu.model.enums.AlgorithmType;
import ru.hoff.edu.model.enums.ResultOutType;

import java.util.List;

public record LoadParcelRequestDto(
        AlgorithmType algorithmType,
        List<String> parcelIds,
        String pathToParcelsFile,
        List<String> trucksDescriptions,
        ResultOutType resultOutType,
        String pathToResultFile) {

}
