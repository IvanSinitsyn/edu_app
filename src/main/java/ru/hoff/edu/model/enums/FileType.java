package ru.hoff.edu.model.enums;

/**
 * Перечисление, представляющее типы файлов, которые могут быть использованы в системе.
 */
public enum FileType {

    /**
     * Текстовый файл в формате TXT.
     */
    TXT,

    /**
     * Файл в формате JSON.
     */
    JSON;

    /**
     * Преобразует строку в соответствующее значение перечисления {@link FileType}.
     *
     * @param value Строковое представление типа файла (например, "txt", "json").
     * @return Соответствующее значение перечисления {@link FileType}.
     * @throws IllegalArgumentException если переданное значение не соответствует ни одному из типов файлов.
     */
    public static FileType fromString(String value) {
        try {
            return FileType.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unknown result out type: " + value, e);
        }
    }
}
