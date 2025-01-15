package ru.hoff.edu.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.hoff.edu.domain.Parcel;
import ru.hoff.edu.domain.Truck;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.hoff.edu.util.DataConverter.convertFormToString;

@Slf4j
@RequiredArgsConstructor
public class JsonFileReportWriter implements ReportWriter {

    private final String outputPath;
    private final ObjectWriter JSON_WRITER = new ObjectMapper().writerWithDefaultPrettyPrinter();

    @Override
    public String writeReport(List<Truck> trucks) {
        try {
            File file = new File(outputPath);

            if (file.exists()) {
                if (file.delete()) {
                    log.info("Existing file deleted: {}", outputPath);
                } else {
                    log.error("Failed to delete existing file: {}", outputPath);
                    throw new RuntimeException("Failed to delete existing file: " + outputPath);
                }
            }

            if (!file.createNewFile()) {
                log.error("Failed to create new file: {}", outputPath);
                throw new RuntimeException("Failed to create new file: " + outputPath);
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
                    parcelData.put("form", convertFormToString(parcel.getForm()));
                    parcelDataList.add(parcelData);
                }

                truckContent.put("parcels", parcelDataList);

                Map<String, Object> truckData = new HashMap<>();
                truckData.put("truck", truckContent);

                truckDataList.add(truckData);
            }

            JSON_WRITER.writeValue(file, truckDataList);

            return "Report saved to " + outputPath;
        } catch (IOException ex) {
            log.error("Error while writing to {}", outputPath, ex);
            throw new RuntimeException("Error while writing to: " + outputPath, ex);
        }
    }
}
