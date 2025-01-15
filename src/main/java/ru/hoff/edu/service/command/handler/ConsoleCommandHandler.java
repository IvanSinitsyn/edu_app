package ru.hoff.edu.service.command.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.hoff.edu.dto.BaseCommandDto;
import ru.hoff.edu.service.command.Command;
import ru.hoff.edu.util.CommandParser;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class ConsoleCommandHandler implements CommandHandler<Void> {

    private final String HELP_COMMAND = "help";
    private final CommandParser commandParser;
    private final Map<String, Command<?, ? extends BaseCommandDto>> commandHandlers;

    @Override
    public Void handleCommand(String command) {
        if (HELP_COMMAND.equals(command)) {
            printHelp();
            return null;
        }

        try {
            BaseCommandDto commandDto = commandParser.parseCommand(command);
            Command<?, BaseCommandDto> handler = (Command<?, BaseCommandDto>) commandHandlers.get(commandDto.getCommandType());
            if (handler != null) {
                String result = (String) handler.execute(commandDto);
                System.out.println(result);
            } else {
                System.out.println("Обработчик для команды не найден: " + commandDto.getCommandType());
            }
        } catch (IllegalArgumentException ex) {
            System.err.println("Ошибка: " + ex.getMessage());
        }

        return null;
    }

    private void printHelp() {
        System.out.println("текст в работе");
    }
}
