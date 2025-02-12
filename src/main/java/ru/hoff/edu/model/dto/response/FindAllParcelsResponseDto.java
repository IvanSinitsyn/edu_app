package ru.hoff.edu.model.dto.response;

import ru.hoff.edu.model.dto.ParcelDto;

import java.util.List;


public record FindAllParcelsResponseDto(List<ParcelDto> parcels, int totalPages,
                                        long totalElements) implements BaseResponseDto {
}
