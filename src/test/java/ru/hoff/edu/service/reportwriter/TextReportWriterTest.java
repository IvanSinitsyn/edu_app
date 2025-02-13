package ru.hoff.edu.service.reportwriter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hoff.edu.domain.Parcel;
import ru.hoff.edu.domain.Truck;
import ru.hoff.edu.service.mapper.ParcelMapper;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class TextReportWriterTest {

    private final TextReportWriter reportWriter;

    public TextReportWriterTest() {
        reportWriter = new TextReportWriter(Mappers.getMapper(ParcelMapper.class));
    }

    @Test
    void write_shouldReturnFormattedReport_whenTrucksAndParcelsAreValid() {
        Parcel parcel1 = new Parcel(
                "Parcel1",
                new char[][]{
                        {'X', 'X'},
                        {'X', 'X'}
                },
                "X",
                true
        );

        Truck truck1 = new Truck();
        truck1.place(parcel1, 0, 0);

        Truck truck2 = new Truck();
        truck2.place(new Parcel(
                "Parcel3",
                new char[][]{
                        {' ', 'X', ' '},
                        {'X', 'X', 'X'}
                },
                "X",
                true
        ), 0, 0);

        List<Truck> trucks = List.of(truck1, truck2);

        String expectedReport = "Кузов:" + System.lineSeparator() +
                "6x6" + System.lineSeparator() +
                "XX    " + System.lineSeparator() +
                "XX    " + System.lineSeparator() +
                "      " + System.lineSeparator() +
                "      " + System.lineSeparator() +
                "      " + System.lineSeparator() +
                "      " + System.lineSeparator() +
                "Parcel1" + System.lineSeparator() +
                "XX" + System.lineSeparator() +
                "XX" + System.lineSeparator() +
                "---------------" + System.lineSeparator() +
                "6x6" + System.lineSeparator() +
                " X    " + System.lineSeparator() +
                "XXX   " + System.lineSeparator() +
                "      " + System.lineSeparator() +
                "      " + System.lineSeparator() +
                "      " + System.lineSeparator() +
                "      " + System.lineSeparator() +
                "Parcel3" + System.lineSeparator() +
                " X " + System.lineSeparator() +
                "XXX" + System.lineSeparator() +
                "---------------";

        String actualReport = reportWriter.write(trucks);

        assertThat(actualReport.replaceAll("\\s", ""))
                .isEqualTo(expectedReport.replaceAll("\\s", ""));
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
