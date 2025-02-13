package ru.hoff.edu.service.parser;

import org.springframework.stereotype.Component;

/**
 * Класс FileExtensionParser предоставляет утилитный метод для извлечения расширения файла
 * из его имени. Расширение определяется как часть имени файла, следующая за последней точкой.
 *
 * <p>Если имя файла не содержит точки или равно null, возвращается пустая строка.</p>
 *
 * <p>Пример использования:
 * <pre>
 *     String extension = FileExtensionParser.getFileExtension("example.txt");
 *     // Результат: "txt"
 * </pre>
 * </p>
 */
@Component
public class FileExtensionParser {

    private static final int INDENT_INDEX_FOR_EXTENSION = 1;

    /**
     * Извлекает расширение файла из его имени.
     *
     * <p>Алгоритм:
     * <ul>
     *     <li>Если имя файла равно null или не содержит точки, возвращается пустая строка.</li>
     *     <li>Иначе возвращается подстрока, начиная с символа после последней точки.</li>
     * </ul>
     * </p>
     *
     * @param fileName Имя файла, из которого нужно извлечь расширение.
     * @return Расширение файла или пустая строка, если расширение отсутствует.
     */
    public String getFileExtension(String fileName) {
        if (fileName == null || !fileName.contains(".")) {
            return "";
        }
        return fileName.substring(fileName.lastIndexOf('.') + INDENT_INDEX_FOR_EXTENSION);
    }
}
