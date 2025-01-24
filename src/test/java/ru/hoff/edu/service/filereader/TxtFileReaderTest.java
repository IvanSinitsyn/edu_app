package ru.hoff.edu.service.filereader;

import org.junit.jupiter.api.Test;
import ru.hoff.edu.service.filereader.impl.TxtFileReader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TxtFileReaderTest {

    @Test
    void readFile_Check_Result_IsValid() throws IOException {
        // Arrange
        File tempFile = File.createTempFile("testInputFile", ".txt");
        tempFile.deleteOnExit();

        String fileContent = """
                        Посылка Тип 1
                        Посылка Тип 4
                        КУБ
            """;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            writer.write(fileContent);
        }

        // Act
        TxtFileReader fileReader = new TxtFileReader();
        List<String> parsedData = fileReader.readFile(tempFile.getAbsolutePath());

        // Assert
        assertEquals(3, parsedData.size(), "Должно быть 3 посылки");

        assertEquals("Посылка Тип 1", parsedData.get(0));
        assertEquals("Посылка Тип 4", parsedData.get(1));
        assertEquals("КУБ", parsedData.get(2));
    }
}
