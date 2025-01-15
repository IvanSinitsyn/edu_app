package ru.hoff.edu.util.filereader;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JsonFileReader implements InputFileReader {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public List<String> readFile(String inputFile) {
        try {
            return objectMapper.readValue(
                    new File(inputFile),
                    new TypeReference<>() {
                    }
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
