package ru.hoff.edu.service.reportwriter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.hoff.edu.domain.Parcel;
import ru.hoff.edu.domain.Truck;
import ru.hoff.edu.service.mapper.ParcelMapper;

import java.util.List;

/**
 * Класс TextReportWriter реализует интерфейс {@link ReportWriter} и предназначен для
 * генерации текстового отчета о грузовиках и их содержимом. Отчет включает информацию
 * о размерах грузовиков, их сетке (grid) и списке посылок (parcels), находящихся в каждом грузовике.
 *
 * <p>Каждый грузовик и его содержимое разделяются строкой "---------------".</p>
 *
 * @see ReportWriter
 * @see Truck
 * @see Parcel
 */
@Component
@RequiredArgsConstructor
public class TextReportWriter implements ReportWriter {

    private static final String NEW_LINE = System.lineSeparator();
    private final ParcelMapper parcelMapper;

    /**
     * Генерирует текстовый отчет на основе списка грузовиков.
     *
     * <p>Отчет включает:
     * <ul>
     *     <li>Размер каждого грузовика.</li>
     *     <li>Сетку (grid) грузовика в строковом формате.</li>
     *     <li>Список посылок (parcels) в грузовике с их названиями и формами.</li>
     * </ul>
     * </p>
     *
     * @param trucks Список грузовиков, для которых формируется отчет.
     * @return Текстовый отчет в виде строки.
     */
    @Override
    public String write(List<Truck> trucks, String outPath) {
        return writeToString(trucks);
    }

    private String writeToString(List<Truck> trucks) {
        StringBuilder summary = new StringBuilder();
        summary.append("Кузов:");
        summary.append(NEW_LINE);

        for (Truck truck : trucks) {
            summary.append(truck.showTruckSize())
                    .append(NEW_LINE)
                    .append(parcelMapper.convertArrayToString(truck.getGrid()))
                    .append(NEW_LINE);

            List<Parcel> parcels = truck.getParcels();
            for (Parcel parcel : parcels) {
                summary.append(parcel.getName())
                        .append(NEW_LINE)
                        .append(parcelMapper.convertArrayToString(parcel.getForm()))
                        .append(NEW_LINE);
            }

            summary.append("---------------");
            summary.append(NEW_LINE);
        }

        return summary.toString();
    }
}
