package ru.hoff.edu.dto;

import lombok.Builder;
import ru.hoff.edu.model.enums.CommandType;

/**
 * Класс, представляющий DTO (Data Transfer Object) для команды выгрузки посылок (Parcels) из файла.
 * Используется для передачи данных, необходимых для выполнения выгрузки посылок.
 */
@Builder
public record UnloadParcelsCommandDto(String inFileName, String outFileName,
                                      boolean withCount) implements BaseCommandDto {

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
