package ru.hoff.edu.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.hoff.edu.model.enums.CommandType;

/**
 * Класс, представляющий DTO (Data Transfer Object) для команды редактирования посылки (Parcel).
 * Используется для передачи данных, необходимых для изменения существующей посылки.
 */
@Getter
@RequiredArgsConstructor
public class EditParcelCommandDto implements BaseCommandDto {

    private final String id;
    private final String name;
    private final String form;
    private final String symbol;

    /**
     * Возвращает тип команды. В данном случае всегда возвращает {@link CommandType#EDIT}.
     *
     * @return Тип команды — {@link CommandType#EDIT}.
     */
    @Override
    public CommandType getCommandType() {
        return CommandType.EDIT;
    }
}
