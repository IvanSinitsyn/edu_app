package ru.hoff.edu.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.hoff.edu.model.enums.CommandType;

/**
 * Класс, представляющий DTO (Data Transfer Object) для запроса поиска посылки (Parcel) по её названию.
 * Используется для передачи данных, необходимых для поиска существующей посылки.
 */
@Getter
@RequiredArgsConstructor
public class FindParcelByIdQueryDto implements BaseCommandDto {

    private final String parcelName;

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
