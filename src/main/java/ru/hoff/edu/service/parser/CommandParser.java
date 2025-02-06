package ru.hoff.edu.service.parser;

import org.springframework.stereotype.Component;
import ru.hoff.edu.service.request.Request;

/**
 * Интерфейс для парсеров команд.
 * Определяет метод для разбора строки команды и преобразования её в DTO (Data Transfer Object).
 */
@Component
public interface CommandParser {

    /**
     * Разбирает строку команды и преобразует её в объект DTO.
     *
     * @param command Строка команды, которую необходимо разобрать.
     * @return Объект DTO, содержащий данные команды.
     */
    Request parse(String command);
}
