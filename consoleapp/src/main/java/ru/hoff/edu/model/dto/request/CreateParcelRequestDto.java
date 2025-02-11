package ru.hoff.edu.model.dto.request;

import lombok.Builder;

@Builder
public record CreateParcelRequestDto(String name, String form, String symbol) {
}