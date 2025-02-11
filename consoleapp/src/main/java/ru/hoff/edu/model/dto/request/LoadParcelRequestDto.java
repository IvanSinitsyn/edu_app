package ru.hoff.edu.model.dto.request;

public record LoadParcelRequestDto(
        String userId,
        String algorithmType,
        String parcelIds,
        String pathToParcelsFile,
        String trucksDescriptions,
        String resultOutType,
        String pathToResultFile) {
}
