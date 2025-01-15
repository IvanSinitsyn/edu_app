package ru.hoff.edu.service.command.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.hoff.edu.dto.BaseCommandDto;
import ru.hoff.edu.service.command.Command;
import ru.hoff.edu.util.CommandParser;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class TelegramCommandHandler implements CommandHandler<String> {

    private final String HELP_COMMAND = "/help";
    private final CommandParser commandParser;
    private final Map<String, Command<?, ? extends BaseCommandDto>> commandHandlers;

    @Override
    public String handleCommand(String command) {
        if (HELP_COMMAND.equals(command)) {
            return getHelpMessage();
        }

        try {
            BaseCommandDto commandDto = commandParser.parseCommand(command);
            Command<?, BaseCommandDto> handler = (Command<?, BaseCommandDto>) commandHandlers.get(commandDto.getCommandType());
            if (handler != null) {
                return (String) handler.execute(commandDto);
            } else {
                return "Обработчик для команды не найден: " + commandDto.getCommandType();
            }
        } catch (IllegalArgumentException ex) {
            return "Ошибка: " + ex.getMessage();
        }
    }

    private String getHelpMessage() {
        return "текст в работе";
    }

}
