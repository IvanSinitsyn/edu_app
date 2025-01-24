package ru.hoff.edu.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.hoff.edu.model.enums.CommandType;

/**
 * Класс, представляющий DTO (Data Transfer Object) для команды удаления посылки (Parcel).
 * Используется для передачи данных, необходимых для удаления существующей посылки.
 */
@Getter
@AllArgsConstructor
public class DeleteParcelCommandDto implements BaseCommandDto {

    private final String parcelName;

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
