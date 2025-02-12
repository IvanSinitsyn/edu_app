package ru.hoff.edu.service.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.hoff.edu.model.TruckData;
import ru.hoff.edu.service.exception.JsonFileReaderException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TruckDataParserTest {

    @TempDir
    Path tempDir;
    @Mock
    private ObjectMapper objectMapper;
    private TruckDataParser truckDataParser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        truckDataParser = new TruckDataParser(objectMapper);
    }

    @Test
    void parse_ShouldSuccessfullyParseJsonFile() throws IOException {
        TruckData truckData1 = new TruckData();
        TruckData truckData2 = new TruckData();
        TruckData[] trucks = {truckData1, truckData2};

        File jsonFile = tempDir.resolve("trucks.json").toFile();
        jsonFile.createNewFile();

        when(objectMapper.readValue(jsonFile, TruckData[].class)).thenReturn(trucks);

        List<TruckData> result = truckDataParser.parse(jsonFile.getAbsolutePath());

        assertThat(result).containsExactly(truckData1, truckData2);
        verify(objectMapper, times(1)).readValue(jsonFile, TruckData[].class);
    }

    @Test
    void parse_ShouldThrowException_WhenFileDoesNotExist() {
        String invalidPath = tempDir.resolve("non_existing.json").toString();

        assertThatThrownBy(() -> truckDataParser.parse(invalidPath))
                .isInstanceOf(JsonFileReaderException.class)
                .hasMessageContaining("Ошибка при чтении JSON файла: " + invalidPath);
    }

    @Test
    void parse_ShouldThrowException_WhenJsonIsInvalid() throws IOException {
        File invalidJsonFile = tempDir.resolve("invalid.json").toFile();
        invalidJsonFile.createNewFile();

        doThrow(new IOException("Invalid JSON"))
                .when(objectMapper)
                .readValue(invalidJsonFile, TruckData[].class);

        assertThatThrownBy(() -> truckDataParser.parse(invalidJsonFile.getAbsolutePath()))
                .isInstanceOf(JsonFileReaderException.class)
                .hasMessageContaining("Ошибка при чтении JSON файла");
    }
}
