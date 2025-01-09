package ru.hoff.edu.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.hoff.edu.domain.Parcel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class InputFileParser {

    private final ParcelConverter parcelConverter;
    private final ParcelSorter parcelSorter;

    public List<Parcel> parseParcels(List<Map<String, Object>> fileData) {
        List<Parcel> parcels = new ArrayList<>();

        for (Map<String, Object> dataEntry : fileData) {
            log.info("Parsing entry: {}", dataEntry);

            Parcel parcel = parcelConverter.convertToParcel(dataEntry);
            parcels.add(parcel);
        }

        return parcelSorter.sortDesc(parcels);
    }
}
