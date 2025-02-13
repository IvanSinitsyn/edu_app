package ru.hoff.edu.model.dto.response;

import lombok.Builder;
import ru.hoff.edu.model.dto.ParcelDto;

import java.util.List;

@Builder
public record FindAllParcelsResponseDto(List<ParcelDto> parcels, int totalPages,
                                        long totalElements) implements ResponseDto {

    @Override
    public String execute() {
        StringBuilder result = new StringBuilder();
        for (ParcelDto parcel : parcels) {
            result.append("Имя посылки: ").append(parcel.name()).append("\n");
            result.append("форма посылки:\n");
            for (char[] row : parcel.form()) {
                for (char cell : row) {
                    result.append(cell);
                }
                result.append("\n");
            }
            result.append("---------\n");
        }
        return result.toString();
    }
}
