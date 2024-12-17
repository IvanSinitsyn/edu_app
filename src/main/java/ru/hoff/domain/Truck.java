package ru.hoff.domain;

public class Truck {
    public static final int WIDTH = 6;
    public static final int HEIGHT = 6;
    private final char[][] grid;

    public Truck() {
        grid = new char[WIDTH][HEIGHT];
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                grid[j][i] = ' ';
            }
        }
    }

    public boolean canPlace(char[][] packageShape, int x, int y) {
        int packageHeight = packageShape.length;
        int packageWidth = packageShape[0].length;

        if (x + packageWidth > WIDTH || y + packageHeight > HEIGHT) {
            return false;
        }

        for (int i = 0; i < packageHeight; i++) {
            for (int j = 0; j < packageWidth; j++) {
                if (packageShape[i][j] != ' ' && grid[y + i][x + j] != ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    public char[][] getGrid() {
        return grid;
    }

    public void place(char[][] packageShape, int x, int y) {
        int packageHeight = packageShape.length;
        int packageWidth = packageShape[0].length;

        for (int i = 0; i < packageHeight; i++) {
            for (int j = 0; j < packageWidth; j++) {
                if (packageShape[i][j] != ' ') {
                    grid[y + i][x + j] = packageShape[i][j];
                }
            }
        }
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

    public void print() {
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
}
