package ru.hoff.edu.model.dto;

import lombok.Builder;

/**
 * Класс ParcelDto представляет собой объект передачи данных (DTO) для сущности Parcel.
 */
@Builder
public record ParcelDto(String name, char[][] form, String symbol, boolean isLoaded) {
}
