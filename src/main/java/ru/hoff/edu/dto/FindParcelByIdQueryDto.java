package ru.hoff.edu.dto;

import lombok.Builder;
import ru.hoff.edu.model.enums.CommandType;

/**
 * Запись, представляющая DTO (Data Transfer Object) для запроса поиска посылки (Parcel) по её названию.
 * Используется для передачи данных, необходимых для поиска существующей посылки.
 */
@Builder
public record FindParcelByIdQueryDto(String parcelName) implements BaseCommandDto {

    /**
     * Возвращает тип команды. В данном случае всегда возвращает {@link CommandType#FIND}.
     *
     * @return Тип команды — {@link CommandType#FIND}.
     */
    @Override
    public CommandType getCommandType() {
        return CommandType.FIND;
    }
}
