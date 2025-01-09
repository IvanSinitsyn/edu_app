package ru.hoff.edu.util.filereader;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class JsonFileReaderTest {

    @Test
    void testReadFile() throws IOException {
        ObjectMapper objectMapper = mock(ObjectMapper.class);

        String jsonContent = "[{\"name\":\"parcel1\",\"form\":\"1\",\"symbol\":\"1\"},"
                + "{\"name\":\"parcel2\",\"form\":\"2\",\"symbol\":\"2\"}]";

        // Мокаем поведение ObjectMapper: когда будет вызван метод readValue, возвращаем список карт
        TypeReference<List<Map<String, Object>>> typeReference = new TypeReference<List<Map<String, Object>>>() {};
        List<Map<String, Object>> expectedResult = List.of(
                Map.of("name", "parcel1", "form", "1", "symbol", "1"),
                Map.of("name", "parcel2", "form", "2", "symbol", "2")
        );

        when(objectMapper.readValue(any(File.class), eq(typeReference))).thenReturn(expectedResult);

        // Создаем экземпляр JsonFileReader, передаем мокаемый ObjectMapper
        JsonFileReader jsonFileReader = new JsonFileReader();

        // Вызываем метод readFile
        List<Map<String, Object>> result = jsonFileReader.readFile("test.json");

        // Проверяем, что результат соответствует ожидаемому
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("parcel1", result.get(0).get("name"));
        assertEquals("1", result.get(0).get("form"));
        assertEquals("1", result.get(0).get("symbol"));

        // Проверяем, что метод ObjectMapper был вызван с правильными аргументами
        verify(objectMapper).readValue(any(File.class), eq(typeReference));
    }
}
