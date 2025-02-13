package ru.hoff.edu.model.dto.request;

import lombok.Builder;
import ru.hoff.edu.service.Request;

/**
 * Запись, представляющая DTO (Data Transfer Object) для команды редактирования посылки (Parcel).
 * Используется для передачи данных, необходимых для изменения существующей посылки.
 */
@Builder
public record EditParcelRequestDto(String id, String name, String form, String symbol) implements Request {
}

