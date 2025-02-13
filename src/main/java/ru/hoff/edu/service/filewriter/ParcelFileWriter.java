package ru.hoff.edu.service.filewriter;

import org.springframework.stereotype.Component;
import ru.hoff.edu.service.exception.TxtFileIOException;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;

@Component
public class ParcelFileWriter {

    public void write(String outputFilePath, List<String> data) {
        try (BufferedWriter writer = new BufferedWriter(new java.io.FileWriter(outputFilePath))) {
            for (String line : data) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new TxtFileIOException("Ошибка при записи посылок в файл", e);
        }
    }
}
