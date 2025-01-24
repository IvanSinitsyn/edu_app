package ru.hoff.edu.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.hoff.edu.model.enums.CommandType;

/**
 * Класс, представляющий DTO (Data Transfer Object) для команды выгрузки посылок (Parcels) из файла.
 * Используется для передачи данных, необходимых для выполнения выгрузки посылок.
 */
@Getter
@RequiredArgsConstructor
public class UnloadParcelsCommandDto implements BaseCommandDto {

    private final String inFileName;
    private final String outFileName;
    private final boolean withCount;

    /**
     * Возвращает тип команды. В данном случае всегда возвращает {@link CommandType#UNLOAD}.
     *
     * @return Тип команды — {@link CommandType#UNLOAD}.
     */
    @Override
    public CommandType getCommandType() {
        return CommandType.UNLOAD;
    }
}
