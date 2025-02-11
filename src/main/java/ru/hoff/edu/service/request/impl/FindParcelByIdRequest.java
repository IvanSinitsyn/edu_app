package ru.hoff.edu.service.request.impl;

import lombok.Builder;
import ru.hoff.edu.service.request.Request;

@Builder
public record FindParcelByIdRequest(String parcelName) implements Request {
}
