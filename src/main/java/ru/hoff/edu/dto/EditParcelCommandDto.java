package ru.hoff.edu.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import ru.hoff.edu.model.enums.CommandType;

/**
 * Запись, представляющая DTO (Data Transfer Object) для команды редактирования посылки (Parcel).
 * Используется для передачи данных, необходимых для изменения существующей посылки.
 */
@Builder
public record EditParcelCommandDto(String id, String name, String form, String symbol) implements BaseCommandDto {

    /**
     * Возвращает тип команды. В данном случае всегда возвращает {@link CommandType#EDIT}.
     *
     * @return Тип команды — {@link CommandType#EDIT}.
     */
    @JsonIgnore
    @Override
    public CommandType getCommandType() {
        return CommandType.EDIT;
    }
}
