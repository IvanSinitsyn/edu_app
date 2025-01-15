package ru.hoff.edu.service;

import ru.hoff.edu.domain.Parcel;
import ru.hoff.edu.domain.Truck;
import ru.hoff.edu.util.DataConverter;

import java.util.List;

public class TextReportWriter implements ReportWriter {

    @Override
    public String writeReport(List<Truck> trucks) {
        StringBuilder summary = new StringBuilder();
        summary.append("Кузов:\n");

        for (Truck truck : trucks) {
            summary.append(truck.showTruckSize()).append("\n")
                    .append(DataConverter.convertFormToString(truck.getGrid())).append("\n");

            List<Parcel> parcels = truck.getParcels();
            for (Parcel parcel : parcels) {
                summary.append(parcel.getName()).append("\n")
                        .append(DataConverter.convertFormToString(parcel.getForm())).append("\n");
            }

            summary.append("---------------\n");
        }

        return summary.toString();
    }
}
