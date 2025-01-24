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

    /**
     * Преобразует текстовое описание команды в соответствующее значение перечисления {@link CommandType}.
     *
     * @param description Текстовое описание команды.
     * @return Соответствующее значение перечисления {@link CommandType}.
     * @throws IllegalArgumentException если переданное описание не соответствует ни одной команде.
     */
    public static CommandType fromDescription(String description) {
        for (CommandType commandType : CommandType.values()) {
            if (commandType.getDescription().equals(description)) {
                return commandType;
            }
        }
        throw new IllegalArgumentException("No enum constant with description: " + description);
    }

    /**
     * Возвращает текстовое описание команды.
     *
     * @return Текстовое описание команды.
     */
    public String getDescription() {
        return description;
    }
}
