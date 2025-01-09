package ru.hoff.edu.util.filewriter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.extern.slf4j.Slf4j;
import ru.hoff.edu.domain.Parcel;
import ru.hoff.edu.domain.Truck;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.hoff.edu.util.DataConverter.convertShapeToStrings;

@Slf4j
public class JsonFileWriter implements FileWriter<Truck> {

    private final ObjectWriter JSON_WRITER = new ObjectMapper().writerWithDefaultPrettyPrinter();

    public void writeToFile(String filePath, List<Truck> trucks) {
        try {
            File file = new File(filePath);

            if (file.exists()) {
                if (file.delete()) {
                    System.out.println("Existing file deleted: " + filePath);
                } else {
                    System.out.println("Failed to delete existing file: " + filePath);
                    return;
                }
            }

            if (!file.createNewFile()) {
                System.out.println("Failed to create new file: " + filePath);
                return;
            }

            List<Map<String, Object>> truckDataList = new ArrayList<>();

            for (int i = 0; i < trucks.size(); i++) {
                Truck truck = trucks.get(i);

                Map<String, Object> truckContent = new HashMap<>();
                truckContent.put("id", i + 1);

                List<Map<String, Object>> parcelDataList = new ArrayList<>();
                for (Parcel parcel : truck.getParcels()) {
                    Map<String, Object> parcelData = new HashMap<>();
                    parcelData.put("name", parcel.getName());
                    parcelData.put("form", convertShapeToStrings(parcel.getForm()));
                    parcelDataList.add(parcelData);
                }

                truckContent.put("parcels", parcelDataList);

                Map<String, Object> truckData = new HashMap<>();
                truckData.put("truck", truckContent);

                truckDataList.add(truckData);
            }

            JSON_WRITER.writeValue(file, truckDataList);
            System.out.println("Wrote to " + filePath);

        } catch (IOException ex) {
            System.out.println("Error while writing to " + filePath);
            log.error("Error while writing to {}", filePath, ex);
        }
    }
}
