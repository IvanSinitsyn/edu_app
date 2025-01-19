package ru.hoff.edu.service;

import ru.hoff.edu.domain.Parcel;
import ru.hoff.edu.domain.Truck;
import ru.hoff.edu.util.DataConverter;

import java.util.List;

public class TextReportWriter implements ReportWriter {

    private static final String NEW_LINE = "\n";

    @Override
    public String write(List<Truck> trucks) {
        StringBuilder summary = new StringBuilder();
        summary.append("Кузов:");
        summary.append(NEW_LINE);

        for (Truck truck : trucks) {
            summary.append(truck.showTruckSize())
                    .append(NEW_LINE)
                    .append(DataConverter.convertFormToString(truck.getGrid()))
                    .append(NEW_LINE);

            List<Parcel> parcels = truck.getParcels();
            for (Parcel parcel : parcels) {
                summary.append(parcel.getName())
                        .append(NEW_LINE)
                        .append(DataConverter.convertFormToString(parcel.getForm()))
                        .append(NEW_LINE);
            }

            summary.append("---------------");
            summary.append(NEW_LINE);
        }

        return summary.toString();
    }
}
