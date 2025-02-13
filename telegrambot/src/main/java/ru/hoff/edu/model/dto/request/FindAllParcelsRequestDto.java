package ru.hoff.edu.model.dto.request;

import lombok.Builder;
import ru.hoff.edu.service.Request;

@Builder
public record FindAllParcelsRequestDto(int page, int size) implements Request {
}
