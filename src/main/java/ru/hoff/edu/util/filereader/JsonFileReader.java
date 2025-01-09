package ru.hoff.edu.util.filereader;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class JsonFileReader implements InputFileReader {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public List<Map<String, Object>> readFile(String inputFile) throws IOException {
        return objectMapper.readValue(
                new File(inputFile),
                new TypeReference<List<Map<String, Object>>>() {}
        );
    }
}
