package ru.hoff.edu.model.dto.response;

import ru.hoff.edu.model.dto.ParcelDto;

public record EditParcelResponseDto(ParcelDto parcel) implements ResponseDto {
}
