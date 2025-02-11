package ru.hoff.edu.model.dto.request;

import lombok.Builder;
import ru.hoff.edu.service.Request;

@Builder
public record LoadParcelRequestDto(
        String userId,
        String algorithmType,
        String parcelIds,
        String pathToParcelsFile,
        String trucksDescriptions,
        String resultOutType,
        String pathToResultFile) implements Request {
}
