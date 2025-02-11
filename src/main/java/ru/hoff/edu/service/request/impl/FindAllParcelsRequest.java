package ru.hoff.edu.service.request.impl;

import lombok.Builder;
import ru.hoff.edu.service.request.Request;

@Builder
public record FindAllParcelsRequest(int page, int size) implements Request {
}
