package ru.hoff.edu.util;

import ru.hoff.edu.domain.Parcel;

import java.util.ArrayList;
import java.util.List;

public class DataConverter {

    public static String parcelToString(Parcel parcel) {
        char[][] parcelShape = parcel.getForm();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < parcelShape.length; i++) {
            sb.append("|");
            for (int j = 0; j < parcelShape[i].length; j++) {
                sb.append(parcelShape[i][j]);
            }
            sb.append("|");
        }
        return sb.toString();
    }

    public static List<String> convertShapeToStrings(char[][] shape) {
        List<String> shapeStrings = new ArrayList<>();
        for (char[] row : shape) {
            shapeStrings.add(new String(row));
        }
        return shapeStrings;
    }
}
