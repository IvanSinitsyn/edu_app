package ru.hoff.edu.service.filereader;

import java.util.List;

/**
 * Интерфейс для чтения данных из файла.
 * Определяет метод для чтения данных из файла и возвращения списка наименований посылок.
 */
public interface InputFileReader {

    /**
     * Читает данные из файла и возвращает список наименований посылок.
     *
     * @param inputFile Путь к входному файлу.
     * @return Список наименований посылок, прочитанных из файла.
     */
    List<String> readFile(String inputFile);
}
