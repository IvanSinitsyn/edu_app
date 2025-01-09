package ru.hoff.edu.domain;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class Truck {
    public static final int WIDTH = 6;
    public static final int HEIGHT = 6;
    private int id;
    private final List<Parcel> parcels;

    @Getter
    private final char[][] grid;

    public Truck() {
        parcels = new ArrayList<>();
        grid = new char[WIDTH][HEIGHT];
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                grid[j][i] = ' ';
            }
        }
    }

    public boolean canPlace(Parcel parcel, int gridX, int gridY) {
        int packageHeight = parcel.getHeight();
        int packageWidth = parcel.getWidth();

        if (gridX + packageWidth > WIDTH || gridY + packageHeight > HEIGHT) {
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

    public List<Parcel> getParcels() {
        return parcels;
    }

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

    public boolean isEmpty() {
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                if (grid[i][j] != ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    public void showLoadingResult() {
        System.out.println("+++++++");
        for (int i = HEIGHT - 1; i >= 0; i--) {
            System.out.print("+");
            for (int j = 0; j < WIDTH; j++) {
                System.out.print(grid[i][j]);
            }
            System.out.println("+");
        }
        System.out.println("+++++++");
    }

    public int getCurrentLoad() {
        int currentLoad = 0;
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                if (grid[y][x] != ' ') { // Если ячейка занята
                    currentLoad++;
                }
            }
        }
        return currentLoad;
    }

    public int getHalfCapacity() {
        return Truck.HEIGHT * Truck.WIDTH / 2;
    }
}
