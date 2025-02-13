package ru.hoff.edu.model.dto.request;

import ru.hoff.edu.model.enums.AlgorithmType;
import ru.hoff.edu.model.enums.ResultOutType;

public record LoadParcelRequestDto(
        String userId,
        AlgorithmType algorithmType,
        String parcelIds,
        String pathToParcelsFile,
        String trucksDescriptions,
        ResultOutType resultOutType,
        String pathToResultFile) {

}
