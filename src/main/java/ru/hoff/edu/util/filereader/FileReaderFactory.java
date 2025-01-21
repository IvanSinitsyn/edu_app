package ru.hoff.edu.util.filereader;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import ru.hoff.edu.model.enums.FileType;

@Component
public class FileReaderFactory {

    public InputFileReader createFileReader(FileType type) {
        return switch (type) {
            case JSON -> new JsonFileReader(new ObjectMapper());
            case TXT -> new TxtFileReader();
        };
    }
}
