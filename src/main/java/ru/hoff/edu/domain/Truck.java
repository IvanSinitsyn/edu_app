package ru.hoff.edu.domain;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Truck {
    public static final int WIDTH = 6;
    public static final int HEIGHT = 6;
    private final int width;
    private final int height;
    private final List<Parcel> parcels;
    private final char[][] grid;

    public Truck() {
        parcels = new ArrayList<>();
        grid = new char[WIDTH][HEIGHT];
        width = WIDTH;
        height = HEIGHT;
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                grid[j][i] = ' ';
            }
        }
    }

    public Truck(String description) {
        parcels = new ArrayList<>();
        String[] dimensions = description.split("x");
        if (dimensions.length != 2) {
            throw new IllegalArgumentException("Invalid input format. Expected format: NxM (e.g., 3x3)");
        }

        int rows = Integer.parseInt(dimensions[0]);
        width = rows;
        int cols = Integer.parseInt(dimensions[1]);
        height = cols;
        grid = new char[rows][cols];
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                grid[j][i] = ' ';
            }
        }
    }

    public static List<Truck> createTrucksByDescription(List<String> description) {
        List<Truck> trucks = new ArrayList<>();
        for (String desc : description) {
            trucks.add(new Truck(desc));
        }

        return trucks;
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

    public String showTruckSize() {
        return this.getHeight() + "x" + this.getWidth();
    }
}
