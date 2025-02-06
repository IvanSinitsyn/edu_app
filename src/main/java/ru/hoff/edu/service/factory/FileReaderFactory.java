package ru.hoff.edu.service.factory;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.hoff.edu.model.enums.FileType;
import ru.hoff.edu.service.filereader.InputFileReader;

import java.util.Map;

/**
 * Фабрика для создания читателей файлов.
 * В зависимости от типа файла создает соответствующий читатель.
 */
@Component
@RequiredArgsConstructor
public class FileReaderFactory {

    private final Map<FileType, InputFileReader> inputFileReaderMap;

    /**
     * Создает читатель файлов на основе переданного типа файла.
     *
     * @param type Тип файла (например, JSON или TXT).
     * @return Читатель файлов, соответствующий переданному типу.
     */
    public InputFileReader createFileReader(FileType type) {
        return inputFileReaderMap.get(type);
    }
}
