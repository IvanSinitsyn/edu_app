package ru.hoff.edu.service;

import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.hoff.edu.domain.Parcel;
import ru.hoff.edu.domain.Truck;
import ru.hoff.edu.service.exception.DeleteFileException;
import ru.hoff.edu.service.exception.FileCreationException;
import ru.hoff.edu.service.exception.JsonFileWriterException;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Класс, реализующий запись данных о грузовиках и посылках в JSON-файл.
 * Использует {@link ObjectWriter} для преобразования данных в JSON-формат.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JsonFileReportWriter implements ReportWriter {

    private final ObjectWriter objectWriter;
    private final ParcelMapper parcelMapper;

    /**
     * Записывает данные о грузовиках и посылках в JSON-файл.
     *
     * @param trucks Список грузовиков, данные о которых необходимо записать.
     * @return Сообщение о результате записи (например, путь к файлу или ошибка).
     * @throws RuntimeException если возникает ошибка ввода-вывода при записи в файл.
     */
    @Override
    public String write(List<Truck> trucks, String outputPath) {
        try {
            File file = prepareFile(outputPath);
            List<Map<String, Object>> truckDataList = buildTruckDataList(trucks);

            try (BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file))) {
                objectWriter.writeValue(bufferedOutputStream, truckDataList);
                bufferedOutputStream.flush();
            }

            return "Report saved to " + outputPath;
        } catch (IOException ex) {
            throw new JsonFileWriterException("Error while writing to: " + outputPath, ex);
        }
    }

    private File prepareFile(String path) throws IOException {
        File file = new File(path);
        if (file.exists() && !file.delete()) {
            throw new DeleteFileException("Failed to delete existing file: " + path);
        }
        if (!file.createNewFile()) {
            throw new FileCreationException("Failed to create new file: " + path);
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
            parcelData.put("form", parcelMapper.convertArrayToString(parcel.getForm()));
            parcelDataList.add(parcelData);
        }
        return parcelDataList;
    }
}
