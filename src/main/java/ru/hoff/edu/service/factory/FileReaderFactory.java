package ru.hoff.edu.service.factory;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import ru.hoff.edu.model.enums.FileType;
import ru.hoff.edu.service.filereader.InputFileReader;
import ru.hoff.edu.service.filereader.impl.JsonFileReader;
import ru.hoff.edu.service.filereader.impl.TxtFileReader;

/**
 * Фабрика для создания читателей файлов.
 * В зависимости от типа файла создает соответствующий читатель.
 */
@Component
public class FileReaderFactory {

    /**
     * Создает читатель файлов на основе переданного типа файла.
     *
     * @param type Тип файла (например, JSON или TXT).
     * @return Читатель файлов, соответствующий переданному типу.
     */
    public InputFileReader createFileReader(FileType type) {
        return switch (type) {
            case JSON -> new JsonFileReader(new ObjectMapper());
            case TXT -> new TxtFileReader();
        };
    }
}
