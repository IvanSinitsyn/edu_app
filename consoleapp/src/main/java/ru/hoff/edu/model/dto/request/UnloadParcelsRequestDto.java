package ru.hoff.edu.model.dto.request;

import lombok.Builder;

@Builder
public record UnloadParcelsRequestDto(String inFileName, String outFileName, boolean withCount) {
}
