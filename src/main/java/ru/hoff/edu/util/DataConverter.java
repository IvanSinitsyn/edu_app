package ru.hoff.edu.util;

public class DataConverter {

    public static String packageToString(char[][] packageShape) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < packageShape.length; i++) {
            sb.append("|");
            for (int j = 0; j < packageShape[i].length; j++) {
                sb.append(packageShape[i][j]);
            }
            sb.append("|");
        }
        return sb.toString();
    }
}
