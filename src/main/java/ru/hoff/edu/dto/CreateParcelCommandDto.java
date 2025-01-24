package ru.hoff.edu.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import ru.hoff.edu.model.enums.CommandType;

/**
 * Класс, представляющий DTO (Data Transfer Object) для команды создания посылки (Parcel).
 * Используется для передачи данных, необходимых для создания новой посылки.
 */
@Getter
@AllArgsConstructor
@Builder
public class CreateParcelCommandDto implements BaseCommandDto {
    private final String name;
    private final String form;
    private final String symbol;

    /**
     * Возвращает тип команды. В данном случае всегда возвращает {@link CommandType#CREATE}.
     *
     * @return Тип команды — {@link CommandType#CREATE}.
     */
    @Override
    public CommandType getCommandType() {
        return CommandType.CREATE;
    }
}
