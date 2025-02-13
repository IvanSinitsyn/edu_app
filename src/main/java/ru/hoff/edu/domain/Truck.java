package ru.hoff.edu.domain;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс, представляющий грузовик (Truck), который может перевозить посылки (Parcel).
 * Грузовик имеет фиксированные размеры (ширину и высоту), а также сетку (grid),
 * которая отображает расположение посылок в грузовике.
 */
@Getter
public class Truck {

    /**
     * Ширина грузовика по умолчанию.
     */
    private static final int WIDTH = 6;

    /**
     * Высота грузовика по умолчанию.
     */
    private static final int HEIGHT = 6;
    private final int width;
    private final int height;
    private final List<Parcel> parcels;
    private final char[][] grid;

    /**
     * Конструктор по умолчанию. Создает грузовик с размерами по умолчанию (6x6).
     * Инициализирует пустую сетку и список посылок.
     */
    public Truck() {
        parcels = new ArrayList<>();
        grid = new char[WIDTH][HEIGHT];
        width = WIDTH;
        height = HEIGHT;
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                grid[i][j] = ' ';
            }
        }
    }

    /**
     * Конструктор, который создает грузовик с размерами, указанными в строке описания.
     * Ожидаемый формат строки: "NxM", где N — высота, M — ширина.
     *
     * @param rows Высота грузовика.
     * @param cols Ширина грузовика.
     * @throws IllegalArgumentException если строка описания имеет неверный формат.
     */
    public Truck(int rows, int cols) {
        parcels = new ArrayList<>();
        height = rows;
        width = cols;
        grid = new char[rows][cols];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                grid[i][j] = ' ';
            }
        }
    }

    /**
     * Проверяет, можно ли разместить посылку в грузовике на указанных координатах.
     *
     * @param parcel Посылка, которую нужно разместить.
     * @param gridX  Координата X в сетке грузовика.
     * @param gridY  Координата Y в сетке грузовика.
     * @return true, если посылку можно разместить, иначе false.
     */
    public boolean canPlace(Parcel parcel, int gridX, int gridY) {
        int packageHeight = parcel.getHeight();
        int packageWidth = parcel.getWidth();

        if (gridX + packageWidth > width || gridY + packageHeight > height) {
            return false;
        }

        for (int i = 0; i < packageHeight; i++) {
            for (int j = 0; j < packageWidth; j++) {
                if (parcel.getForm()[i][j] != ' ' && grid[gridY + i][gridX + j] != ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Размещает посылку в грузовике на указанных координатах.
     *
     * @param parcel Посылка, которую нужно разместить.
     * @param x      Координата X в сетке грузовика.
     * @param y      Координата Y в сетке грузовика.
     */
    public void place(Parcel parcel, int x, int y) {
        int packageHeight = parcel.getHeight();
        int packageWidth = parcel.getWidth();

        for (int i = 0; i < packageHeight; i++) {
            for (int j = 0; j < packageWidth; j++) {
                if (parcel.getForm()[i][j] != ' ') {
                    grid[y + i][x + j] = parcel.getForm()[i][j];
                }
            }
        }

        parcels.add(parcel);
    }

    /**
     * Проверяет, пуст ли грузовик.
     *
     * @return true, если грузовик пуст, иначе false.
     */
    public boolean isEmpty() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (grid[i][j] != ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Возвращает текущую загрузку грузовика (количество занятых ячеек в сетке).
     *
     * @return Количество занятых ячеек.
     */
    public int getCurrentLoad() {
        int currentLoad = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (grid[y][x] != ' ') { // Если ячейка занята
                    currentLoad++;
                }
            }
        }
        return currentLoad;
    }

    /**
     * Возвращает половину вместимости грузовика (половину от общего количества ячеек).
     *
     * @return Половина вместимости грузовика.
     */
    public int getHalfCapacity() {
        return height * width / 2;
    }

    /**
     * Возвращает строку с размерами грузовика в формате "NxM".
     *
     * @return Строка с размерами грузовика.
     */
    public String showTruckSize() {
        return this.getHeight() + "x" + this.getWidth();
    }
}
