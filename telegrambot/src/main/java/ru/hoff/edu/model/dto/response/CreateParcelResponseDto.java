package ru.hoff.edu.model.dto.response;

import ru.hoff.edu.model.dto.ParcelDto;

public record CreateParcelResponseDto(ParcelDto parcel) implements BaseResponseDto {
}
