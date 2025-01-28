package ru.hoff.edu.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import ru.hoff.edu.model.enums.CommandType;

/**
 * Класс, представляющий DTO (Data Transfer Object) для команды создания посылки (Parcel).
 * Используется для передачи данных, необходимых для создания новой посылки.
 */
@Builder
public record CreateParcelCommandDto(String name, String form, String symbol) implements BaseCommandDto {

    /**
     * Возвращает тип команды. В данном случае всегда возвращает {@link CommandType#CREATE}.
     *
     * @return Тип команды — {@link CommandType#CREATE}.
     */
    @JsonIgnore
    @Override
    public CommandType getCommandType() {
        return CommandType.CREATE;
    }
}
