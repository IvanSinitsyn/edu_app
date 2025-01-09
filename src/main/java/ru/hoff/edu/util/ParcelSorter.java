package ru.hoff.edu.util;

import ru.hoff.edu.domain.Parcel;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class ParcelSorter {

    /**
     * Метод сортировки посылок от большей к меньшей
     * @param parcels список посылок
     * @return отсортированный список посылок
     */
    public List<Parcel> sortDesc(List<Parcel> parcels) {
        if (parcels == null || parcels.isEmpty()) {
            return Collections.emptyList();
        }

        parcels.sort((parcel1, parcel2) -> {
            int compareSize = Integer.compare(parcel2.getHeight(), parcel1.getHeight());
            if (compareSize != 0) {
                return compareSize;
            }

            int sumA = calculateFilledCells(parcel1);
            int sumB = calculateFilledCells(parcel2);
            return Integer.compare(sumB, sumA);
        });

        return parcels.stream().toList();
    }

    private int calculateFilledCells(Parcel parcel) {
        return Arrays.stream(parcel.getForm())
                .mapToInt(arr -> (int) IntStream.range(0, arr.length)
                        .filter(i -> arr[i] != ' ')
                        .count())
                .sum();
    }
}
