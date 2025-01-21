package ru.hoff.edu.service.factory;

import lombok.RequiredArgsConstructor;
import ru.hoff.edu.service.parser.CommandParser;
import ru.hoff.edu.service.parser.CreateCommandParser;
import ru.hoff.edu.service.parser.DeleteCommandParser;
import ru.hoff.edu.service.parser.EditCommandParser;
import ru.hoff.edu.service.parser.FindByIdCommandParser;
import ru.hoff.edu.service.parser.LoadCommandParser;
import ru.hoff.edu.service.parser.UnloadCommandParser;
import ru.hoff.edu.util.filereader.FileReaderFactory;

@RequiredArgsConstructor
public class CommandParserFactory {

    private final FileReaderFactory fileReaderFactory;

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
