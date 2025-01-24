package ru.hoff.edu.model.enums;

/**
 * Перечисление, представляющее типы вывода результата.
 * Используется для определения способа вывода данных (например, в текстовом виде или в файл).
 */
public enum ResultOutType {

    /**
     * Результат выводится в текстовом виде (например, в консоль).
     */
    TEXT,

    /**
     * Результат сохраняется в файл.
     */
    FILE;

    /**
     * Преобразует строку в соответствующее значение перечисления {@link ResultOutType}.
     *
     * @param value Строковое представление типа вывода (например, "text", "file").
     * @return Соответствующее значение перечисления {@link ResultOutType}.
     * @throws IllegalArgumentException если переданное значение не соответствует ни одному из типов вывода.
     */
    public static ResultOutType fromString(String value) {
        try {
            return ResultOutType.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unknown result out type: " + value, e);
        }
    }
}
