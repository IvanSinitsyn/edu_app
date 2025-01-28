package ru.hoff.edu.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * Класс ParcelDto представляет собой объект передачи данных (DTO) для сущности Parcel.
 */
@Getter
@Setter
@RequiredArgsConstructor
public class ParcelDto {

    private final String name;
    private final char[][] form;
    private final String symbol;
    private final boolean isLoaded;
}
