package ru.hoff.edu.service.strategy;

import org.springframework.stereotype.Component;
import ru.hoff.edu.domain.Parcel;
import ru.hoff.edu.domain.Truck;

import java.util.List;

/**
 * Интерфейс, определяющий стратегию загрузки посылок в грузовики.
 * Реализации этого интерфейса предоставляют различные алгоритмы загрузки.
 */
@Component
public interface LoadStrategy {

    /**
     * Загружает посылки в грузовики в соответствии с выбранной стратегией.
     *
     * @param parcels Список посылок, которые необходимо загрузить.
     * @param trucks  Список грузовиков, в которые будут загружены посылки.
     * @return Список грузовиков с загруженными посылками.
     */
    List<Truck> loadParcels(List<Parcel> parcels, List<Truck> trucks);
}
