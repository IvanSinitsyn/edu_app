package ru.hoff.edu.validation;

/**
 * Класс ParcelValidator предоставляет методы для проверки валидности формы посылки (Parcel).
 * Основная задача класса — убедиться, что символы в форме посылки расположены корректно,
 * без нарушения правил соседства (например, отсутствия ортогональных соседей для символов,
 * находящихся по диагонали друг от друга).
 */
public class ParcelValidator {

    public static boolean isParcelFormValid(char[][] form, char symbol) {
        int rows = form.length;
        int cols = form[0].length;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (form[i][j] == symbol) {
                    if (isInvalidDiagonal(form, i, j, symbol)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private static boolean isInvalidDiagonal(char[][] form, int row, int col, char symbol) {
        int rows = form.length;
        int cols = form[0].length;

        int[] rowDir = {-1, -1, 1, 1};
        int[] colDir = {-1, 1, -1, 1};

        for (int d = 0; d < 4; d++) {
            int diagRow = row + rowDir[d];
            int diagCol = col + colDir[d];

            if (diagRow >= 0 && diagRow < rows && diagCol >= 0 && diagCol < cols &&
                    form[diagRow][diagCol] == symbol) {

                if (!hasOrthogonalNeighbor(form, diagRow, diagCol, symbol)) {
                    return true;
                }
            }
        }

        return false;
    }

    private static boolean hasOrthogonalNeighbor(char[][] form, int row, int col, char symbol) {
        int rows = form.length;
        int cols = form[0].length;

        int[] rowDir = {-1, 1, 0, 0};
        int[] colDir = {0, 0, -1, 1};

        for (int d = 0; d < 4; d++) {
            int neighborRow = row + rowDir[d];
            int neighborCol = col + colDir[d];

            if (neighborRow >= 0 && neighborRow < rows && neighborCol >= 0 && neighborCol < cols &&
                    form[neighborRow][neighborCol] == symbol) {
                return true;
            }
        }

        return false;
    }
}
