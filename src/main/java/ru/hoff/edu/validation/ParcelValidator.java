package ru.hoff.edu.validation;

import org.springframework.stereotype.Component;

/**
 * Класс ParcelValidator предоставляет методы для проверки валидности формы посылки (Parcel).
 * Основная задача класса — убедиться, что символы в форме посылки расположены корректно,
 * без нарушения правил соседства (например, отсутствия ортогональных соседей для символов,
 * находящихся по диагонали друг от друга).
 */
@Component
public class ParcelValidator {

    private static final int DIAGONAL_DIRECTIONS = 4;
    private static final int BACK_OFFSET = -1;
    private static final int FORWARD_OFFSET = 1;
    private static final int NO_OFFSET = 0;


    public boolean isParcelFormValid(char[][] form, char symbol) {
        int rows = form.length;
        int cols = form[0].length;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (form[i][j] == symbol && isInvalidDiagonal(form, i, j, symbol)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isInvalidDiagonal(char[][] form, int row, int col, char symbol) {
        int rows = form.length;
        int cols = form[0].length;

        int[] rowOffsets = {BACK_OFFSET, BACK_OFFSET, FORWARD_OFFSET, FORWARD_OFFSET};
        int[] colOffsets = {BACK_OFFSET, FORWARD_OFFSET, BACK_OFFSET, FORWARD_OFFSET};

        for (int direction = 0; direction < DIAGONAL_DIRECTIONS; direction++) {
            int diagonalRow = row + rowOffsets[direction];
            int diagonalCol = col + colOffsets[direction];

            boolean isWithinBounds = diagonalRow >= 0 && diagonalRow < rows &&
                    diagonalCol >= 0 && diagonalCol < cols;

            if (isWithinBounds &&
                    form[diagonalRow][diagonalCol] == symbol &&
                    !hasOrthogonalNeighbor(form, diagonalRow, diagonalCol, symbol)) {
                return true;
            }
        }

        return false;
    }

    private boolean hasOrthogonalNeighbor(char[][] form, int row, int col, char symbol) {
        int rows = form.length;
        int cols = form[0].length;

        int[] rowOffsets = {BACK_OFFSET, FORWARD_OFFSET, NO_OFFSET, NO_OFFSET};
        int[] colOffsets = {NO_OFFSET, NO_OFFSET, BACK_OFFSET, FORWARD_OFFSET};

        for (int direction = 0; direction < DIAGONAL_DIRECTIONS; direction++) {
            int neighborRow = row + rowOffsets[direction];
            int neighborCol = col + colOffsets[direction];

            boolean isWithinBounds = neighborRow >= 0 && neighborRow < rows &&
                    neighborCol >= 0 && neighborCol < cols;

            if (isWithinBounds && form[neighborRow][neighborCol] == symbol) {
                return true;
            }
        }

        return false;
    }
}
