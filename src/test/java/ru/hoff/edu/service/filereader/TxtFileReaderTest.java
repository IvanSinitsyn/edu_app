package ru.hoff.edu.service.filereader;

import org.junit.jupiter.api.Test;
import ru.hoff.edu.service.filereader.impl.TxtFileReader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TxtFileReaderTest {

    @Test
    void readFile_Check_Result_IsValid() throws IOException {
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

        TxtFileReader fileReader = new TxtFileReader();
        List<String> parsedData = fileReader.readFile(tempFile.getAbsolutePath());

        assertThat(3).isEqualTo(parsedData.size()).withFailMessage("Должно быть 3 посылки");
        assertThat("Посылка Тип 1").isEqualTo(parsedData.get(0));
        assertThat("Посылка Тип 4").isEqualTo(parsedData.get(1));
        assertThat("КУБ").isEqualTo(parsedData.get(2));
    }
}
