package ru.hoff.edu.dto.response;

import ru.hoff.edu.dto.ParcelDto;

import java.util.List;


public record FindAllParcelsResponseDto(List<ParcelDto> parcels, int totalPages,
                                        long totalElements) implements BaseResponseDto {
}
