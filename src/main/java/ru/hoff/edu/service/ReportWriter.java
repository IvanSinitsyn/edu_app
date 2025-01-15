package ru.hoff.edu.service;

import ru.hoff.edu.domain.Truck;

import java.util.List;

public interface ReportWriter {

    String writeReport(List<Truck> trucks);
}
