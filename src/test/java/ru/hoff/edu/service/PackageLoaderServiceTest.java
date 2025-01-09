package ru.hoff.edu.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.hoff.edu.domain.Parcel;
import ru.hoff.edu.domain.Truck;
import ru.hoff.edu.model.enums.AlgorithmType;
import ru.hoff.edu.util.filereader.TxtFileReader;
import ru.hoff.edu.util.InputFileParser;
import ru.hoff.edu.util.ParcelConverter;
import ru.hoff.edu.util.ParcelSorter;
import ru.hoff.edu.util.filewriter.JsonFileWriter;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PackageLoaderServiceTest {

    private ParcelUploaderService packageUploaderService;

    @BeforeEach
    void setUp() {
        packageUploaderService = new ParcelUploaderService(
                new TxtFileReader(),
                new InputFileParser(
                        new ParcelConverter(),
                        new ParcelSorter()
                ),
                new JsonFileWriter()
        );
    }

    @Test
    void loadPackagesInTrucks_InEasyMode_WithValidInput_ReturnsValidTrucks() {
        // Arrange
        Parcel parcel1 = new Parcel("name1", new char[][]{ {'1'} });
        Parcel parcel2 = new Parcel("name2", new char[][]{ {'2', '2'} });
        Parcel parcel3 = new Parcel("name3", new char[][]{ {'3', '3', '3'} });

        // Act
        List<Truck> trucks = packageUploaderService.loadPackagesInTrucks(Arrays.asList(parcel1, parcel2, parcel3), AlgorithmType.EASY, null);

        // Assert
        assertEquals(3, trucks.size());

        // Truck 1
        char[][] expectedTruck1 = {
                {'1', ' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' '}
        };
        assertArrayEquals(expectedTruck1, trucks.get(0).getGrid());

        // Truck 2
        char[][] expectedTruck2 = {
                {'2', '2', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' '}
        };
        assertArrayEquals(expectedTruck2, trucks.get(1).getGrid());

        // Truck 3
        char[][] expectedTruck3 = {
                {'3', '3', '3', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' '}
        };
        assertArrayEquals(expectedTruck3, trucks.get(2).getGrid());
    }

    @Test
    void loadPackagesInTrucks_InHardMode_WithValidInput_ReturnsValidTrucks() {
        // Arrange
        Parcel parcel1 = new Parcel("name1", new char[][]{ {'1'} });
        Parcel parcel2 = new Parcel("name2", new char[][]{ {'2', '2'} });
        Parcel parcel3 = new Parcel("name3", new char[][]{ {'3', '3', '3'} });
        Parcel parcel4 = new Parcel("name4", new char[][]{ {'4', '4', '4', '4'} });

        // Act
        List<Truck> trucks = packageUploaderService.loadPackagesInTrucks(Arrays.asList(parcel4, parcel3, parcel2, parcel1), AlgorithmType.OPTIMAL, null);

        // Assert
        assertEquals(1, trucks.size()); // Все посылки должны поместиться в один грузовик

        char[][] expectedTruckGrid = {
                {'4', '4', '4', '4', '2', '2'},
                {'3', '3', '3', '1', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' '}
        };

        assertArrayEquals(expectedTruckGrid, trucks.getFirst().getGrid());
    }

    @Test
    void parseParcels_WithValidData_ReturnCorrectParcels() {
        // Arrange
        InputFileParser inputFileParser = new InputFileParser(new ParcelConverter(), new ParcelSorter());

        List<Map<String, Object>> fileData = List.of(
                Map.of("name", "name1", "form", List.of("4444")),
                Map.of("name", "name2", "form", List.of("333")),
                Map.of("name", "name3", "form", List.of("22")),
                Map.of("name", "name4", "form", List.of("1"))
        );

        // Act
        List<Parcel> parcels = inputFileParser.parseParcels(fileData);

        // Assert
        List<Parcel> expectedParcels = Arrays.asList(
                new Parcel("name1", new char[][]{{'4', '4', '4', '4'}}),
                new Parcel("name2", new char[][]{{'3', '3', '3'}}),
                new Parcel("name3", new char[][]{{'2', '2'}}),
                new Parcel("name4", new char[][]{{'1'}})
        );

        assertEquals(expectedParcels.size(), parcels.size());
        for (int i = 0; i < expectedParcels.size(); i++) {
            Assertions.assertTrue(Arrays.deepEquals(expectedParcels.get(i).getForm(), parcels.get(i).getForm()));
            assertEquals(expectedParcels.get(i).getName(), parcels.get(i).getName());
        }
    }
}
