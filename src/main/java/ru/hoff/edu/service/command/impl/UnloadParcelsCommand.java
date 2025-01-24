package ru.hoff.edu.service.command.impl;

import lombok.Getter;
import org.springframework.stereotype.Component;
import ru.hoff.edu.dto.UnloadParcelsCommandDto;
import ru.hoff.edu.service.command.Command;

/**
 * Класс, реализующий обработку команды выгрузки посылок (Parcels) из файла.
 * В текущей реализации метод не выполняет никаких действий и возвращает {@code null}.
 * Заглушка для будущей реализации.
 */
@Component
@Getter
public class UnloadParcelsCommand implements Command<Void, UnloadParcelsCommandDto> {

    /**
     * Выполняет команду выгрузки посылок.
     * В текущей реализации метод не выполняет никаких действий и возвращает {@code null}.
     *
     * @param commandDto DTO команды, содержащее данные для выгрузки посылок.
     * @return Всегда возвращает {@code null}, так как функциональность ещё не реализована.
     */
    @Override
    public Void execute(UnloadParcelsCommandDto commandDto) {
//        try {
//            List<Map<String, Object>> truckDataList = inputFileReader.readFile(command.getInputFile());
//            fileWriter.writeToFile(command.getPathToResultFile(), truckDataList);
//        } catch (IOException ex) {
//            log.error("Error while processing file {}", command.getInputFile(), ex);
//        }
        return null;
    }
}
