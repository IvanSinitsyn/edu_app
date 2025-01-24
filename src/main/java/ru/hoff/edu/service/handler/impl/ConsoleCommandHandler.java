package ru.hoff.edu.service.handler.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.hoff.edu.dto.BaseCommandDto;
import ru.hoff.edu.model.enums.CommandType;
import ru.hoff.edu.service.command.Command;
import ru.hoff.edu.service.handler.CommandHandler;

import java.util.Map;

/**
 * Класс, реализующий обработку команд, введенных через консоль.
 * Использует фабрику парсеров команд и набор обработчиков команд для выполнения соответствующих действий.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ConsoleCommandHandler implements CommandHandler<Void, BaseCommandDto> {

    private final Map<CommandType, Command<?, ? extends BaseCommandDto>> commandHandlers;

    /**
     * Обрабатывает переданную команду и выводит результат в консоль.
     *
     * @param command Команда, которую необходимо обработать.
     * @return Всегда возвращает {@code null}, так как результат выводится в консоль.
     */
    @Override
    public Void handle(BaseCommandDto command) {
        try {
            Command<?, BaseCommandDto> handler = (Command<?, BaseCommandDto>) commandHandlers.get(command.getCommandType());
            if (handler != null) {
                String result = (String) handler.execute(command);
                System.out.println(result);
            } else {
                System.out.println("Обработчик для команды не найден: " + command.getCommandType());
            }
        } catch (IllegalArgumentException ex) {
            log.error("Ошибка: ", ex);
            System.err.println("Ошибка: " + ex.getMessage());
        }

        return null;
    }
}
