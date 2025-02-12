package ru.hoff.edu.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hoff.edu.domain.Parcel;
import ru.hoff.edu.domain.Truck;
import ru.hoff.edu.service.mapper.ParcelMapper;
import ru.hoff.edu.service.reportwriter.TextReportWriter;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class TextReportWriterTest {

    @InjectMocks
    private TextReportWriter reportWriter;

    @Mock
    private ParcelMapper parcelMapper;

    @Test
    void write_shouldReturnFormattedReport_whenTrucksAndParcelsAreValid() {
        // Arrange
        Parcel parcel1 = new Parcel(
                "Parcel1",
                new char[][]{
                        {'X', 'X'},
                        {' ', 'X'}
                },
                "X",
                true
        );

        Parcel parcel2 = new Parcel(
                "Parcel2",
                new char[][]{
                        {'X', ' '},
                        {'X', 'X'}
                },
                "X",
                false
        );

        Truck truck1 = new Truck();
        truck1.place(parcel1, 0, 0);
        truck1.place(parcel2, 0, 0);

        Truck truck2 = new Truck();
        truck2.place(new Parcel(
                "Parcel3",
                new char[][]{
                        {'X', 'X', 'X'},
                        {' ', 'X', ' '}
                },
                "X",
                true
        ), 0, 0);

        List<Truck> trucks = new ArrayList<>();
        trucks.add(truck1);
        trucks.add(truck2);

        String expectedReport = "Кузов:" + System.lineSeparator() + "6x6" + System.lineSeparator() + "[XX    , XX    ,       ,       ,       ,       ]" + System.lineSeparator() +
                "Parcel1" + System.lineSeparator() + "[XX,  X]" + System.lineSeparator() + "Parcel2" + System.lineSeparator() + "[X , XX]" + System.lineSeparator() +
                "---------------" + System.lineSeparator() + "6x6" + System.lineSeparator() + "[XXX   ,  X    ,       ,       ,       ,       ]" + System.lineSeparator() +
                "Parcel3" + System.lineSeparator() + "[XXX,  X ]" + System.lineSeparator() + "---------------";

        // Act
        String actualReport = reportWriter.write(trucks);

        // Assert
        assertThat(actualReport).isEqualTo(expectedReport);
    }

    @Test
    void write_shouldReturnEmptyReport_whenTruckListIsEmpty() {
        // Arrange
        List<Truck> trucks = new ArrayList<>();
        String expectedReport = "Кузов:" + System.lineSeparator();

        // Act
        String actualReport = reportWriter.write(trucks);

        // Assert
        assertEquals(expectedReport, actualReport);
    }
}
