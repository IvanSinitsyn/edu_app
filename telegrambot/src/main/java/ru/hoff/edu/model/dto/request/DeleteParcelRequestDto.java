package ru.hoff.edu.model.dto.request;

import lombok.Builder;
import ru.hoff.edu.service.Request;

@Builder
public record DeleteParcelRequestDto(String parcelName) implements Request {
}
