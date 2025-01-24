package ru.hoff.edu.dto;

import ru.hoff.edu.model.enums.CommandType;

/**
 * Базовый интерфейс для всех DTO (Data Transfer Object), представляющих команды.
 * Определяет общий метод для получения типа команды.
 */
public interface BaseCommandDto {

    /**
     * Возвращает тип команды, связанный с данным DTO.
     *
     * @return Тип команды, представленный перечислением {@link CommandType}.
     */
    CommandType getCommandType();
}
