package ru.hoff.edu.util.filereader;

import ru.hoff.edu.model.enums.FileType;

public class FileReaderFactory {

    public InputFileReader createFileReader(FileType type) {
        return switch (type) {
            case JSON -> new JsonFileReader();
            case TXT -> new TxtFileReader();
        };
    }
}
