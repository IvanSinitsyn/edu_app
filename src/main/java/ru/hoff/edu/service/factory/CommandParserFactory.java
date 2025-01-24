package ru.hoff.edu.service.factory;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.hoff.edu.service.parser.CommandParser;
import ru.hoff.edu.service.parser.impl.CreateCommandParser;
import ru.hoff.edu.service.parser.impl.DeleteCommandParser;
import ru.hoff.edu.service.parser.impl.EditCommandParser;
import ru.hoff.edu.service.parser.impl.FindByIdCommandParser;
import ru.hoff.edu.service.parser.impl.LoadCommandParser;
import ru.hoff.edu.service.parser.impl.UnloadCommandParser;

/**
 * Фабрика для создания парсеров команд.
 * В зависимости от типа команды создает соответствующий парсер.
 */
@Component
@RequiredArgsConstructor
public class CommandParserFactory {

    private final FileReaderFactory fileReaderFactory;

    /**
     * Создает парсер команд на основе переданной команды.
     *
     * @param command Команда, для которой необходимо создать парсер.
     * @return Парсер команд, соответствующий типу команды.
     * @throws IllegalArgumentException если команда неизвестна.
     */
    public CommandParser createCommandParser(String command) {
        return switch (command.split(" ")[0]) {
            case "/create" -> new CreateCommandParser();
            case "/delete" -> new DeleteCommandParser();
            case "/edit" -> new EditCommandParser();
            case "/find" -> new FindByIdCommandParser();
            case "/load" -> new LoadCommandParser(fileReaderFactory);
            case "/unload" -> new UnloadCommandParser();
            default -> throw new IllegalArgumentException("Неизвестная команда: " + command);
        };
    }
}
