package ru.hoff.edu.service.filereader.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.hoff.edu.service.exception.TxtFileIOException;
import ru.hoff.edu.service.exception.TxtFileNotFoundException;
import ru.hoff.edu.service.filereader.InputFileReader;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс, реализующий чтение данных из текстового файла.
 * Используется для чтения списка наименований посылок из файла в формате TXT.
 */
@Slf4j
@Component
public class TxtFileReader implements InputFileReader {

    /**
     * Читает данные из текстового файла и возвращает список наименований посылок.
     *
     * @param inputFile Путь к текстовому файлу.
     * @return Список наименований посылок, прочитанных из файла.
     * @throws RuntimeException если файл не найден или возникает ошибка ввода-вывода.
     */
    @Override
    public List<String> readFile(String inputFile) {
        List<String> parcelNames = new ArrayList<>();

        InputStream inputStream;
        try {
            inputStream = getClass().getClassLoader().getResourceAsStream(inputFile);
            if (inputStream == null) {
                inputStream = new FileInputStream(inputFile);
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (!line.trim().isEmpty()) {
                        parcelNames.add(line.trim());
                    }
                }
            }
        } catch (FileNotFoundException e) {
            throw new TxtFileNotFoundException("Файл с названиями посылок не неайден", e);
        } catch (IOException e) {
            throw new TxtFileIOException("Ошибка при чтении файла с посылками", e);
        }

        return parcelNames;
    }
}
