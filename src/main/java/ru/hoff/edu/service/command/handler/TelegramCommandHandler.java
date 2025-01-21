package ru.hoff.edu.service.command.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.hoff.edu.dto.BaseCommandDto;
import ru.hoff.edu.service.command.Command;
import ru.hoff.edu.util.CommandParser;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class TelegramCommandHandler implements CommandHandler<String> {

    private final CommandParser commandParser;
    private final Map<String, Command<?, ? extends BaseCommandDto>> commandHandlers;

    @Override
    public String handleCommand(String command) {
        try {
            BaseCommandDto commandDto = commandParser.parseCommand(command);
            Command<?, BaseCommandDto> handler = (Command<?, BaseCommandDto>) commandHandlers.get(commandDto.getCommandType());
            if (handler != null) {
                return (String) handler.execute(commandDto);
            } else {
                return "Обработчик для команды не найден: " + commandDto.getCommandType();
            }
        } catch (IllegalArgumentException ex) {
            log.error("Ошибка: ", ex);
            return "Ошибка: " + ex.getMessage();
        }
    }
}
