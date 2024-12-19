package ru.hoff.edu.util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class PackageSorter {

    /**
     * Метод сортировки посылок от большей к меньшей
     * @param packages список посылок
     * @return отсортированный список посылок
     */
    public List<char[][]> sortDesc(List<char[][]> packages) {
        packages.sort((a, b) -> {
            int compareSize = Integer.compare(b.length, a.length);
            if (compareSize != 0) {
                return compareSize;
            }

            int sumA = Arrays.stream(a)
                    .mapToInt(arr -> (int) IntStream.range(0, arr.length)
                            .filter(i -> arr[i] != ' ')
                            .count())
                    .sum();

            int sumB = Arrays.stream(b)
                    .mapToInt(arr -> (int) IntStream.range(0, arr.length)
                            .filter(i -> arr[i] != ' ')
                            .count())
                    .sum();
            return Integer.compare(sumB, sumA);
        });

        return packages;
    }
}
