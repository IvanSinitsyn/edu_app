package ru.hoff.edu.model.dto.request;

import lombok.Builder;
import ru.hoff.edu.service.Request;

@Builder
public record CreateParcelRequestDto(String name, String form, String symbol) implements Request {
}