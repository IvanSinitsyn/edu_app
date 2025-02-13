package ru.hoff.edu.model.dto.response;

import ru.hoff.edu.model.dto.ParcelDto;

public record FindParcelByIdResponseDto(ParcelDto parcel) implements ResponseDto {

    @Override
    public String execute() {
        StringBuilder result = new StringBuilder();
        result.append("Имя посылки: ").append(parcel.name()).append("\n");
        result.append("форма посылки:\n");
        for (char[] row : parcel.form()) {
            for (char cell : row) {
                result.append(cell);
            }
            result.append("\n");
        }
        result.append("---------\n");
        return result.toString();
    }
}

