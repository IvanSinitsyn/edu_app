package ru.hoff.edu.model.dto.response;

import lombok.Builder;

@Builder
public record UnloadParcelsResponseDto(String result) implements BaseResponseDto {
}
