package ru.hoff.edu.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.hoff.edu.domain.Parcel;
import ru.hoff.edu.domain.Truck;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TextReportWriterTest {

    private TextReportWriter reportWriter;

    @BeforeEach
    void setUp() {
        reportWriter = new TextReportWriter();
    }

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

        String expectedReport = """
                Кузов:
                6x6
                [XX    , XX    ,       ,       ,       ,       ]
                Parcel1
                [XX,  X]
                Parcel2
                [X , XX]
                ---------------
                6x6
                [XXX   ,  X    ,       ,       ,       ,       ]
                Parcel3
                [XXX,  X ]
                ---------------
                """;

        // Act
        String actualReport = reportWriter.write(trucks);

        // Assert
        assertEquals(expectedReport, actualReport);
    }

    @Test
    void write_shouldReturnEmptyReport_whenTruckListIsEmpty() {
        // Arrange
        List<Truck> trucks = new ArrayList<>();
        String expectedReport = "Кузов:\n";

        // Act
        String actualReport = reportWriter.write(trucks);

        // Assert
        assertEquals(expectedReport, actualReport);
    }
}
