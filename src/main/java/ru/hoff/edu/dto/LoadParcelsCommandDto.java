package ru.hoff.edu.dto;

import lombok.Builder;
import ru.hoff.edu.model.enums.AlgorithmType;
import ru.hoff.edu.model.enums.CommandType;
import ru.hoff.edu.model.enums.ResultOutType;

import java.util.List;

/**
 * Запись, представляющая DTO (Data Transfer Object) для команды загрузки посылок (Parcels) в грузовики (Trucks).
 * Используется для передачи данных, необходимых для выполнения алгоритма загрузки посылок.
 */
@Builder
public record LoadParcelsCommandDto(
        AlgorithmType algorithmType,
        List<String> parcelIds,
        List<String> trucksDescriptions,
        ResultOutType resultOutType,
        String pathToResultFile) implements BaseCommandDto {

    /**
     * Возвращает тип команды. В данном случае всегда возвращает {@link CommandType#LOAD}.
     *
     * @return Тип команды — {@link CommandType#LOAD}.
     */
    @Override
    public CommandType getCommandType() {
        return CommandType.LOAD;
    }
}
