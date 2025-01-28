package ru.hoff.edu.dto;

import lombok.Builder;
import ru.hoff.edu.model.enums.CommandType;

/**
 * Запись, представляющая DTO (Data Transfer Object) для запроса поиска всех посылок (Parcel).
 * Используется для передачи данных, необходимых для всех посылок.
 */
@Builder
public record FindAllParcelsQueryDto(int page, int size) implements BaseCommandDto {

    /**
     * Возвращает тип команды. В данном случае всегда возвращает {@link CommandType#FINDALL}.
     *
     * @return Тип команды — {@link CommandType#FINDALL}.
     */
    @Override
    public CommandType getCommandType() {
        return CommandType.FINDALL;
    }
}
