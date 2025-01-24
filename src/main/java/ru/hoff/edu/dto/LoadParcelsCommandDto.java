package ru.hoff.edu.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.hoff.edu.model.enums.AlgorithmType;
import ru.hoff.edu.model.enums.CommandType;
import ru.hoff.edu.model.enums.ResultOutType;

import java.util.List;

/**
 * Класс, представляющий DTO (Data Transfer Object) для команды загрузки посылок (Parcels) в грузовики (Trucks).
 * Используется для передачи данных, необходимых для выполнения алгоритма загрузки посылок.
 */
@Getter
@RequiredArgsConstructor
public class LoadParcelsCommandDto implements BaseCommandDto {

    private final AlgorithmType algorithmType;
    private final List<String> parcelIds;
    private final List<String> trucksDescriptions;
    private final ResultOutType resultOutType;
    private final String pathToResultFile;

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
