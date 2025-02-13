package ru.hoff.edu.service.mediator.request.impl;

import lombok.Builder;
import ru.hoff.edu.service.mediator.request.Request;

@Builder
public record UnloadParcelsRequestDto(String userId, String inFileName, String outFileName,
                                      boolean withCount) implements Request {
}
