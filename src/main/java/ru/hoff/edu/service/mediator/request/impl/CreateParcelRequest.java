package ru.hoff.edu.service.mediator.request.impl;

import lombok.Builder;
import ru.hoff.edu.service.mediator.request.Request;

@Builder
public record CreateParcelRequest(String name, String form, String symbol) implements Request {
}
