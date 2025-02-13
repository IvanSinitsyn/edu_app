package ru.hoff.edu.model.dto.request;

import lombok.Builder;
import ru.hoff.edu.service.Request;

@Builder
public record UnloadParcelsRequestDto(String userId, String inFileName, String outFileName,
                                      boolean withCount) implements Request {
}
