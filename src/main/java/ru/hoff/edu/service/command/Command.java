package ru.hoff.edu.service.command;

import ru.hoff.edu.dto.BaseCommandDto;

/**
 * Интерфейс для обработчиков команд.
 * Определяет общий метод для выполнения команд и возвращения результата.
 *
 * @param <T> Тип результата, возвращаемого обработчиком команды.
 * @param <D> Тип DTO (Data Transfer Object), представляющего команду. Должен наследоваться от {@link BaseCommandDto}.
 */
public interface Command<T, D extends BaseCommandDto> {

    /**
     * Выполняет команду и возвращает результат.
     *
     * @param commandDto DTO команды, содержащее данные для выполнения.
     * @return Результат выполнения команды.
     */
    T execute(D commandDto);
}
