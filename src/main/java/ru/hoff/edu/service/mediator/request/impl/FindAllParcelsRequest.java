package ru.hoff.edu.service.mediator.request.impl;

import lombok.Builder;
import ru.hoff.edu.service.mediator.request.Request;

@Builder
public record FindAllParcelsRequest(int page, int size) implements Request {
}
