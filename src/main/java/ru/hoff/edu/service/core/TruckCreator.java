package ru.hoff.edu.service.core;

import org.springframework.stereotype.Service;
import ru.hoff.edu.domain.Truck;

import java.util.ArrayList;
import java.util.List;

/**
 * Сервис для создания грузовиков.
 */
@Service
public class TruckCreator {

    private static final String TRUCK_SIZE_DELIMITER = "x";

    /**
     * Создает список грузовиков на основе списка строк описания.
     *
     * @param description Список строк описания размеров грузовиков.
     * @return Список созданных грузовиков.
     */
    public List<Truck> createTrucksByDescription(List<String> description) {
        List<Truck> trucks = new ArrayList<>();

        for (String desc : description) {
            String[] dimensions = desc.split(TRUCK_SIZE_DELIMITER);
            if (dimensions.length != 2) {
                throw new IllegalArgumentException("Invalid input format. Expected format: NxM (e.g., 3x3)");
            }

            int rows = Integer.parseInt(dimensions[0]);
            int cols = Integer.parseInt(dimensions[1]);
            trucks.add(new Truck(rows, cols));
        }

        return trucks;
    }
}
