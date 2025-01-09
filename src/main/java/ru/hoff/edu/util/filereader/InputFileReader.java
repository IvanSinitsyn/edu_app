package ru.hoff.edu.util.filereader;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface InputFileReader {

    /**
     * Метод для чтения данных из файла.
     *
     * @param inputFile путь к входному файлу.
     * @return список объектов, представляющих данные.
     * @throws IOException если произошла ошибка чтения.
     */
    List<Map<String, Object>> readFile(String inputFile) throws IOException;
}
