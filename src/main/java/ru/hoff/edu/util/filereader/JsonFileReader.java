package ru.hoff.edu.util.filereader;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;

@RequiredArgsConstructor
public class JsonFileReader implements InputFileReader {

    private final ObjectMapper objectMapper;

    @Override
    public List<String> readFile(String inputFile) {
        try {
            return objectMapper.readValue(
                    new File(inputFile),
                    new TypeReference<>() {
                    }
            );
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
