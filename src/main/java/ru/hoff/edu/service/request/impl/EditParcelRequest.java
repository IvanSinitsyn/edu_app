package ru.hoff.edu.service.request.impl;

import lombok.Builder;
import ru.hoff.edu.service.request.Request;

@Builder
public record EditParcelRequest(String id, String name, String form, String symbol) implements Request {
}
