package ru.hoff.edu.util;

import lombok.extern.slf4j.Slf4j;
import ru.hoff.edu.domain.Parcel;

import java.util.List;
import java.util.Map;

@Slf4j
public class ParcelConverter {

    public Parcel convertToParcel(Map<String, Object> parcelData) {
        String name = (String) parcelData.get("name");
        List<String> parcelDetails = (List<String>) parcelData.get("form");

        int height = parcelDetails.size();
        int width = calculateMaxWidth(parcelDetails);

        Parcel parcel = createParcel(parcelDetails, height, width, name);
        logParcel(parcel);

        return parcel;
    }

    private int calculateMaxWidth(List<String> parcelDetails) {
        int maxWidth = 0;
        for (String line : parcelDetails) {
            if (line.length() > maxWidth) {
                maxWidth = line.length();
            }
        }
        return maxWidth;
    }

    private Parcel createParcel(List<String> parcelDetails, int height, int width, String name) {
        char[][] parcelShape = new char[height][width];
        for (int i = 0, reversedI = height - 1; i < height && reversedI >= 0; i++, reversedI--) {
            for (int j = 0; j < width; j++) {
                if (j < parcelDetails.get(reversedI).length()) {
                    parcelShape[i][j] = parcelDetails.get(reversedI).charAt(j);
                } else {
                    parcelShape[i][j] = ' ';
                }
            }
        }
        return new Parcel(name, parcelShape);
    }

    private void logParcel(Parcel parcel) {
        log.info("Found package: {}", DataConverter.parcelToString(parcel));
    }
}
