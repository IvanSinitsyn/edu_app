package ru.hoff.edu.service.factory;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.hoff.edu.model.enums.CommandType;
import ru.hoff.edu.service.parser.CommandParser;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class CommandParserFactory {

    private final Map<CommandType, CommandParser> commandParsers;

    /**
     * Создает парсер команд на основе переданной команды.
     *
     * @param commandType Тип команды, для которой необходимо создать парсер.
     * @return Парсер команд, соответствующий типу команды.
     * @throws IllegalArgumentException если команда неизвестна.
     */
    public CommandParser createCommandParser(CommandType commandType) {
        return commandParsers.get(commandType);
    }
}