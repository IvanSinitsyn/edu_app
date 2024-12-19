package ru.hoff.edu.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.hoff.edu.domain.Truck;
import ru.hoff.edu.enums.Mode;
import ru.hoff.edu.util.InputFileParser;
import ru.hoff.edu.util.PackageConverter;
import ru.hoff.edu.util.PackageSorter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PackageLoaderServiceTest {

    private InputFileParser txtParserMock;
    private PackageLoaderService packageLoaderService;

    @BeforeEach
    void setUp() {
        txtParserMock = Mockito.mock(InputFileParser.class);
        packageLoaderService = new PackageLoaderService(txtParserMock);
    }

    @Test
    void testLoadPackageEasyMode() {
        // Arrange
        char[][] package1 = {
                {'1'}
        };

        char[][] package2 = {
                {'2', '2'}
        };

        char[][] package3 = {
                {'3', '3', '3'}
        };

        // Act
        List<Truck> trucks = packageLoaderService.loadPackagesInTrucks(Arrays.asList(package1, package2, package3), Mode.EASY);

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
    void testLoadPackagesHardMode() {
        // Arrange
        char[][] package1 = {
                {'1'}
        };

        char[][] package2 = {
                {'2', '2'}
        };

        char[][] package3 = {
                {'3', '3', '3'}
        };

        char[][] package4 = {
                {'4', '4', '4', '4'}
        };

        // Act
        List<Truck> trucks = packageLoaderService.loadPackagesInTrucks(Arrays.asList(package4, package3, package2, package1), Mode.HARD);

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
    void parsePackageFromFile_WithValidData_ReturnCorrectPackages() throws Exception {
        // Arrange
        InputFileParser inputFileParser = new InputFileParser(new PackageConverter(), new PackageSorter());

        Path tempFile = Files.createTempFile("test-file", ".txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile.toFile()))) {
            writer.write("""
                    1
                    
                    22
                    
                    333
                    
                    4444
                    """);
        }


        // Act
        List<char[][]> packages = inputFileParser.parsePackageFromFile(tempFile.toString());

        // Assert
        List<char[][]> expected = Arrays.asList(
                new char[][]{{'4', '4', '4', '4'}},
                new char[][]{{'3', '3', '3'}},
                new char[][]{{'2', '2'}},
                new char[][]{{'1'}});

        assertEquals(expected.size(), packages.size());
        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i).length, packages.get(i).length);
            for (int j = 0; j < expected.get(i).length; j++) {
                assertEquals(Arrays.toString(expected.get(i)[j]), Arrays.toString(packages.get(i)[j]));
            }
        }
        Files.deleteIfExists(tempFile);
    }
}