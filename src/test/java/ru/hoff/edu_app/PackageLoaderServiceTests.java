package ru.hoff.edu_app;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.hoff.domain.Truck;
import ru.hoff.enums.Mode;
import ru.hoff.service.PackageLoaderService;
import ru.hoff.util.TxtParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class PackageLoaderServiceTests {

    private TxtParser txtParserMock;
    private PackageLoaderService packageLoaderService;

    @BeforeEach
    void setUp() {
        txtParserMock = Mockito.mock(TxtParser.class);
        packageLoaderService = new PackageLoaderService(txtParserMock);
    }

    @Test
    void testLoadPackageEasyMode() {
        // Arrange
        String inputFile = "dummyPath";
        char[][] package1 = {
                {'1'}
        };

        char[][] package2 = {
                {'2', '2'}
        };

        char[][] package3 = {
                {'3', '3', '3'}
        };

        when(txtParserMock.parsePackageFromFile(inputFile)).thenReturn(Arrays.asList(package1, package2, package3));

        // Act
        List<Truck> trucks = packageLoaderService.loadPackages(inputFile, Mode.EASY);

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
        String inputFile = "dummyPath";
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

        when(txtParserMock.parsePackageFromFile(inputFile)).thenReturn(Arrays.asList(package4, package3, package2, package1));

        // Act
        List<Truck> trucks = packageLoaderService.loadPackages(inputFile, Mode.HARD);

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

        assertArrayEquals(expectedTruckGrid, trucks.get(0).getGrid());
    }
}
