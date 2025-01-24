package ru.hoff.edu.util;

import ru.hoff.edu.domain.Parcel;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс DataConverter предоставляет утилитные методы для преобразования данных,
 * связанных с посылками (Parcel) и их формами (shape). Включает методы для конвертации
 * между строковым представлением и двумерными массивами символов, а также для работы
 * с шириной и высотой форм.
 *
 * <p>Основные функции:
 * <ul>
 *     <li>Преобразование посылки (Parcel) в строку.</li>
 *     <li>Преобразование двумерного массива символов (формы) в список строк.</li>
 *     <li>Преобразование строки в двумерный массив символов (форму).</li>
 *     <li>Расчет максимальной ширины формы на основе списка строк.</li>
 * </ul>
 * </p>
 *
 * <p>Все методы являются статическими, что позволяет использовать их без создания экземпляра класса.</p>
 */
public class DataConverter {

    /**
     * Преобразует посылку (Parcel) в строковое представление.
     * Форма посылки (двумерный массив символов) преобразуется в строку,
     * где каждая строка массива заключена в символы "|".
     *
     * @param parcel Посылка, которую нужно преобразовать.
     * @return Строковое представление формы посылки.
     */
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

    /**
     * Преобразует двумерный массив символов (форму) в список строк.
     * Каждая строка массива преобразуется в отдельную строку списка.
     *
     * @param shape Двумерный массив символов, представляющий форму.
     * @return Список строк, где каждая строка соответствует строке массива.
     */
    public static List<String> convertFormToString(char[][] shape) {
        List<String> shapeStrings = new ArrayList<>();
        for (char[] row : shape) {
            shapeStrings.add(new String(row));
        }
        return shapeStrings;
    }

    /**
     * Преобразует строку в двумерный массив символов (форму).
     * Строка разделяется на строки по символам новой строки (\n или \r\n).
     * Если строка пустая или null, возвращается пустой массив.
     *
     * @param input Входная строка, представляющая форму.
     * @return Двумерный массив символов, представляющий форму.
     */
    public static char[][] convertStringToForm(String input) {
        if (input == null || input.isEmpty()) {
            return new char[][]{};
        }

        input = input.replace("\\n", "\n");
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

    /**
     * Вычисляет максимальную ширину формы на основе списка строк.
     * Ширина определяется как длина самой длинной строки в списке.
     *
     * @param parcelDetails Список строк, представляющих форму.
     * @return Максимальная ширина формы.
     */
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
