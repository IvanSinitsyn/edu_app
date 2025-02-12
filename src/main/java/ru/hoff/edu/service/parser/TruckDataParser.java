package ru.hoff.edu.service.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.hoff.edu.model.TruckData;
import ru.hoff.edu.service.exception.JsonFileReaderException;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TruckDataParser {

    private final ObjectMapper objectMapper;

    public List<TruckData> parse(String filePath) {
        File file = new File(filePath);
        try {
            return Arrays.asList(objectMapper.readValue(file, TruckData[].class));
        } catch (IOException e) {
            throw new JsonFileReaderException("Ошибка при чтении JSON файла", e);
        }
    }
}
