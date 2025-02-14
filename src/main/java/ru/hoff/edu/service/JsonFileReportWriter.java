package ru.hoff.edu.service;

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
    private final ObjectWriter jsonWriter;

    @Override
    public String write(List<Truck> trucks) {
        try {
            File file = prepareFile(outputPath);
            List<Map<String, Object>> truckDataList = buildTruckDataList(trucks);
            jsonWriter.writeValue(file, truckDataList);
            return "Report saved to " + outputPath;
        } catch (IOException ex) {
            log.error("Error while writing to {}", outputPath, ex);
            throw new RuntimeException("Error while writing to: " + outputPath, ex);
        }
    }

    private File prepareFile(String path) throws IOException {
        File file = new File(path);
        if (file.exists() && !file.delete()) {
            log.error("Failed to delete existing file: {}", path);
            throw new RuntimeException("Failed to delete existing file: " + path);
        }
        if (!file.createNewFile()) {
            log.error("Failed to create new file: {}", path);
            throw new RuntimeException("Failed to create new file: " + path);
        }
        log.info("File prepared for writing: {}", path);
        return file;
    }

    private List<Map<String, Object>> buildTruckDataList(List<Truck> trucks) {
        List<Map<String, Object>> truckDataList = new ArrayList<>();
        for (int i = 0; i < trucks.size(); i++) {
            Truck truck = trucks.get(i);
            Map<String, Object> truckData = buildTruckData(i + 1, truck);
            truckDataList.add(truckData);
        }
        return truckDataList;
    }

    private Map<String, Object> buildTruckData(int id, Truck truck) {
        Map<String, Object> truckContent = new HashMap<>();
        truckContent.put("id", id);
        truckContent.put("parcels", buildParcelDataList(truck.getParcels()));

        Map<String, Object> truckData = new HashMap<>();
        truckData.put("truck", truckContent);
        return truckData;
    }

    private List<Map<String, Object>> buildParcelDataList(List<Parcel> parcels) {
        List<Map<String, Object>> parcelDataList = new ArrayList<>();
        for (Parcel parcel : parcels) {
            Map<String, Object> parcelData = new HashMap<>();
            parcelData.put("name", parcel.getName());
            parcelData.put("form", convertFormToString(parcel.getForm()));
            parcelDataList.add(parcelData);
        }
        return parcelDataList;
    }
}
