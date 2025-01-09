package ru.hoff.edu.util.filereader;

import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TxtFileReaderTest {

    @Test
    void readFile_Check_Result_IsValid() throws IOException {
        // Arrange
        File tempFile = File.createTempFile("testInputFile", ".txt");
        tempFile.deleteOnExit();

        String fileContent = """
            Name: parcel1
            Form: 1
            Symbol: 1

            Name: parcel2
            Form: 22
            Symbol: 2

            Name: parcel3
            Form: 333
            Symbol: 3

            Name: parcel4
            Form: 4444
            Symbol: 4

            Name: parcel5
            Form: 55555
            Symbol: 5

            Name: parcel6
            Form:
            666
            666
            Symbol: 6
            """;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            writer.write(fileContent);
        }

        // Act
        TxtFileReader fileReader = new TxtFileReader();
        List<Map<String, Object>> parsedData = fileReader.readFile(tempFile.getAbsolutePath());

        // Assert
        assertEquals(6, parsedData.size(), "Должно быть 6 посылок");

        assertEquals("parcel1", parsedData.get(0).get("name"));
        assertEquals(List.of("1"), parsedData.get(0).get("form"));
        assertEquals("1", parsedData.get(0).get("symbol"));

        assertEquals("parcel2", parsedData.get(1).get("name"));
        assertEquals(List.of("22"), parsedData.get(1).get("form"));
        assertEquals("2", parsedData.get(1).get("symbol"));

        assertEquals("parcel3", parsedData.get(2).get("name"));
        assertEquals(List.of("333"), parsedData.get(2).get("form"));
        assertEquals("3", parsedData.get(2).get("symbol"));

        assertEquals("parcel4", parsedData.get(3).get("name"));
        assertEquals(List.of("4444"), parsedData.get(3).get("form"));
        assertEquals("4", parsedData.get(3).get("symbol"));

        assertEquals("parcel5", parsedData.get(4).get("name"));
        assertEquals(List.of("55555"), parsedData.get(4).get("form"));
        assertEquals("5", parsedData.get(4).get("symbol"));

        assertEquals("parcel6", parsedData.get(5).get("name"));
        assertEquals(List.of("666", "666"), parsedData.get(5).get("form"));
        assertEquals("6", parsedData.get(5).get("symbol"));
    }
}
