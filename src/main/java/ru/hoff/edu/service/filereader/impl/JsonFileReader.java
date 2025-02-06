package ru.hoff.edu.service.filereader.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.hoff.edu.service.exception.JsonFileReaderException;
import ru.hoff.edu.service.filereader.InputFileReader;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;

/**
 * Класс, реализующий чтение данных из JSON-файла.
 * Использует {@link ObjectMapper} для преобразования JSON-данных в список строк.
 */
@Component
@RequiredArgsConstructor
public class JsonFileReader implements InputFileReader {

    private final ObjectMapper objectMapper;

    /**
     * Читает данные из JSON-файла и возвращает список строк.
     *
     * @param inputFile Путь к JSON-файлу.
     * @return Список строк, прочитанных из файла.
     * @throws UncheckedIOException если возникает ошибка ввода-вывода при чтении файла.
     */
    @Override
    public List<String> readFile(String inputFile) {
        try {
            return objectMapper.readValue(
                    new File(inputFile),
                    new TypeReference<>() {
                    }
            );
        } catch (IOException e) {
            throw new JsonFileReaderException("Ошибка при чтении входного JSON файла", e);
        }
    }
}
