package ru.hoff.edu.dto;

import lombok.Builder;
import ru.hoff.edu.model.enums.CommandType;

/**
 * Запись, представляющая DTO (Data Transfer Object) для команды удаления посылки (Parcel).
 * Используется для передачи данных, необходимых для удаления существующей посылки.
 */
@Builder
public record DeleteParcelCommandDto(String parcelName) implements BaseCommandDto {

    /**
     * Возвращает тип команды. В данном случае всегда возвращает {@link CommandType#DELETE}.
     *
     * @return Тип команды — {@link CommandType#DELETE}.
     */
    @Override
    public CommandType getCommandType() {
        return CommandType.DELETE;
    }
}
