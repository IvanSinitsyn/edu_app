package ru.hoff.edu.util.filewriter;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
public class TxtFileWriter implements FileWriter<Map<String, Object>> {

    @Override
    public void writeToFile(String filePath, List<Map<String, Object>> data) throws IOException {
        try (java.io.FileWriter writer = new java.io.FileWriter(filePath)) {
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
