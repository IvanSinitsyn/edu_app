package ru.hoff.edu.model.dto;

import lombok.Builder;
import ru.hoff.edu.model.enums.ChequeType;

@Builder
public record LoadCalculationDto(String clientId, long cellsCount, int trucksCount, int parcelsCount,
                                 ChequeType chequeType) {
}
