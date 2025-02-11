package ru.hoff.edu.model.dto;

/**
 * Класс ParcelDto представляет собой объект передачи данных (DTO) для сущности Parcel.
 */
public record ParcelDto(String name, char[][] form, String symbol, boolean isLoaded) {
}
