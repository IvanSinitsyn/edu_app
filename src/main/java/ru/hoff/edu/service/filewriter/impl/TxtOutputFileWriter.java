package ru.hoff.edu.service.filewriter.impl;

import lombok.extern.slf4j.Slf4j;
import ru.hoff.edu.service.filewriter.OutputFileWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Класс, реализующий запись данных в текстовый файл.
 * Используется для записи информации о посылках и их формах в файл формата TXT.
 */
@Slf4j
public class TxtOutputFileWriter implements OutputFileWriter<Map<String, Object>> {

    /**
     * Записывает данные о посылках и их формах в текстовый файл.
     *
     * @param filePath Путь к файлу, в который будут записаны данные.
     * @param data     Список данных, содержащий информацию о грузовиках и посылках.
     * @throws IOException если возникает ошибка ввода-вывода при записи в файл.
     */
    @Override
    public void write(String filePath, List<Map<String, Object>> data) throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            for (Map<String, Object> truckEntry : data) {
                Map<String, Object> truck = (Map<String, Object>) truckEntry.get("truck");
                if (truck == null) {
                    continue;
                }

                List<Map<String, Object>> parcels = (List<Map<String, Object>>) truck.get("parcels");
                if (parcels == null) {
                    continue;
                }

                for (Map<String, Object> parcel : parcels) {
                    String name = (String) parcel.get("name");
                    List<String> form = (List<String>) parcel.get("form");

                    writer.write("Посылка: " + name + "\n");
                    writer.write("Форма:\n");
                    for (String row : form) {
                        writer.write(row + "\n");
                    }
                    writer.write("\n");
                }
            }
        } catch (IOException ex) {
            System.out.println("Error while writing to " + filePath);
            log.error("Error while writing to {}", filePath, ex);
            throw ex;
        }

        System.out.println("Data successfully written into file " + filePath);
    }
}
