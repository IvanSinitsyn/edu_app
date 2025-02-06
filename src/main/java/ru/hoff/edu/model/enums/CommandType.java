package ru.hoff.edu.model.enums;

import java.util.Arrays;

/**
 * Перечисление, представляющее типы команд, которые могут быть выполнены в системе.
 * Каждый тип команды связан с текстовым описанием, используемым для идентификации команды.
 */
public enum CommandType {

    /**
     * Команда создания посылки.
     */
    CREATE("/create"),

    /**
     * Команда редактирования посылки.
     */
    EDIT("/edit"),

    /**
     * Команда поиска посылки.
     */
    FIND("/find"),

    /**
     * Команда для поиска всех посылок.
     */
    FIND_ALL("/findall"),

    /**
     * Команда удаления посылки.
     */
    DELETE("/delete"),

    /**
     * Команда загрузки посылок в грузовики.
     */
    LOAD("/load"),

    /**
     * Команда выгрузки посылок из файла.
     */
    UNLOAD("/unload");

    private final String command;

    CommandType(String command) {
        this.command = command;
    }

    public static CommandType fromString(String command) {
        return Arrays.stream(values())
                .filter(c -> c.getCommand().equals(command))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Неизвестная команда: " + command));
    }

    public String getCommand() {
        return command;
    }
}
