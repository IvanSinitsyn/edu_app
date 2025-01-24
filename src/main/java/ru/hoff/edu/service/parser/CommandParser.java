package ru.hoff.edu.service.parser;

import ru.hoff.edu.dto.BaseCommandDto;

/**
 * Интерфейс для парсеров команд.
 * Определяет метод для разбора строки команды и преобразования её в DTO (Data Transfer Object).
 */
public interface CommandParser {

    /**
     * Разбирает строку команды и преобразует её в объект DTO.
     *
     * @param command Строка команды, которую необходимо разобрать.
     * @return Объект DTO, содержащий данные команды.
     */
    BaseCommandDto parse(String command);
}
