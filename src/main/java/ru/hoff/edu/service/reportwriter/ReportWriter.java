package ru.hoff.edu.service.reportwriter;

import ru.hoff.edu.domain.Truck;

import java.util.List;

/**
 * Интерфейс для создания отчета.
 * Определяет метод для записи данных о грузовиках в отчет.
 */
public interface ReportWriter {

    String write(List<Truck> trucks, String outPath);

    default String write(List<Truck> trucks) {
        return write(trucks, null);
    }
}
