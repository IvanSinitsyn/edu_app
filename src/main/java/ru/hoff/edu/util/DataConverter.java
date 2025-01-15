package ru.hoff.edu.util;

import ru.hoff.edu.domain.Parcel;

import java.util.ArrayList;
import java.util.List;

public class DataConverter {

    public static String parcelToString(Parcel parcel) {
        char[][] parcelShape = parcel.getForm();
        StringBuilder sb = new StringBuilder();
        for (char[] chars : parcelShape) {
            sb.append("|");
            for (char aChar : chars) {
                sb.append(aChar);
            }
            sb.append("|");
        }
        return sb.toString();
    }

    public static List<String> convertFormToString(char[][] shape) {
        List<String> shapeStrings = new ArrayList<>();
        for (char[] row : shape) {
            shapeStrings.add(new String(row));
        }
        return shapeStrings;
    }

    public static char[][] convertStringToForm(String input) {
        if (input == null || input.isEmpty()) {
            return new char[][]{};
        }

        String[] rows = input.split("\\r?\\n");

        int numRows = rows.length;
        int numCols = calculateMaxWidth(List.of(rows));

        char[][] charArray = new char[numRows][numCols];
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                if (j < rows[i].length()) {
                    charArray[i][j] = rows[i].charAt(j);
                } else {
                    charArray[i][j] = ' ';
                }
            }
        }

        return charArray;
    }

    public static int calculateMaxWidth(List<String> parcelDetails) {
        int maxWidth = 0;
        for (String line : parcelDetails) {
            if (line.length() > maxWidth) {
                maxWidth = line.length();
            }
        }
        return maxWidth;
    }
}
