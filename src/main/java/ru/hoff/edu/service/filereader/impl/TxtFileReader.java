package ru.hoff.edu.service.filereader.impl;

import lombok.extern.slf4j.Slf4j;
import ru.hoff.edu.service.filereader.InputFileReader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Класс, реализующий чтение данных из текстового файла.
 * Используется для чтения списка наименований посылок из файла в формате TXT.
 */
@Slf4j
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

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(inputFile);
             BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputStream)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    parcelNames.add(line.trim());
                }
            }
        } catch (FileNotFoundException e) {
            log.error("File with parcel names not found", e);
            throw new RuntimeException(e);
        } catch (IOException e) {
            log.error("Error while reading parcel names", e);
            throw new RuntimeException(e);
        }

        return parcelNames;
    }
}
