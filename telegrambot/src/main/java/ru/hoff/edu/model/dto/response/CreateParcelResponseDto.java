package ru.hoff.edu.model.dto.response;

import ru.hoff.edu.model.dto.ParcelDto;

public record CreateParcelResponseDto(ParcelDto parcel) implements ResponseDto {

    @Override
    public String execute() {
        return "Посылка: " + parcel.name() + " создана";
    }
}
