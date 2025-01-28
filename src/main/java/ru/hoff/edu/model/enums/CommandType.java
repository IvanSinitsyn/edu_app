package ru.hoff.edu.model.enums;

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
    FINDALL("/findall"),

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

    private final String description;

    CommandType(String description) {
        this.description = description;
    }
}
