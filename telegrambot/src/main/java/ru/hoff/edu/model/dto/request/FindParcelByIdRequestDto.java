package ru.hoff.edu.model.dto.request;

import lombok.Builder;
import ru.hoff.edu.service.Request;

@Builder
public record FindParcelByIdRequestDto(String parcelName) implements Request {
}
