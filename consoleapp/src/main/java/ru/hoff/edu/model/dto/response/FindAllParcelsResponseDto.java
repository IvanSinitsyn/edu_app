package ru.hoff.edu.model.dto.response;

import lombok.Builder;
import ru.hoff.edu.model.dto.ParcelDto;

import java.util.List;

@Builder
public record FindAllParcelsResponseDto(List<ParcelDto> parcels, int totalPages, long totalElements) {
}
