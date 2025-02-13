package ru.hoff.edu.service.mediator.request.impl;

import lombok.Builder;
import ru.hoff.edu.service.mediator.request.Request;

@Builder
public record EditParcelRequest(String id, String name, String form, String symbol) implements Request {
}
