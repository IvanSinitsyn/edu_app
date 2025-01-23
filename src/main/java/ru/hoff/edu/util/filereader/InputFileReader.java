package ru.hoff.edu.util.filereader;

import java.util.List;

public interface InputFileReader {

    /**
     * Метод для чтения данных из файла.
     *
     * @param inputFile путь к входному файлу.
     * @return список наименований посылок.
     */
    List<String> readFile(String inputFile);
}
