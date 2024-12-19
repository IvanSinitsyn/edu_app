package ru.hoff.edu.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.hoff.edu.domain.Truck;
import ru.hoff.edu.enums.Mode;
import ru.hoff.edu.util.InputFileParser;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class PackageLoaderService {

    private final InputFileParser inputFileParser;

    public void packageProcessingFromFile(String inputFile, boolean isEasyMode) {
        Mode mode = isEasyMode ? Mode.EASY : Mode.HARD;
        List<char[][]> packages = inputFileParser.parsePackageFromFile(inputFile);
        List<Truck> trucks = loadPackagesInTrucks(packages, mode);
        makeLoadingReport(trucks);
    }

    public List<Truck> loadPackagesInTrucks(List<char[][]> packages, Mode mode) {
        if (packages.isEmpty()) {
            log.info("No packages found");
            return new ArrayList<>();
        }

        List<Truck> trucks;
        if (mode == Mode.EASY) {
            trucks = loadWithEasyMode(packages);
        } else {
            trucks = loadWithHardMode(packages);
        }

        return trucks;
    }

    private void makeLoadingReport(List<Truck> trucks) {
        for (int i = 0; i < trucks.size(); i++) {
            System.out.println("Truck " + (i + 1) + ":");
            trucks.get(i).showLoadingResult();
        }
    }

    private List<Truck> loadWithEasyMode(List<char[][]> packages) {
        List<Truck> trucks = new ArrayList<>();
        log.info("Start loading trucks with easy mode");
        for (char[][] packageShape : packages) {
            Truck newTruck = new Truck();
            newTruck.place(packageShape, 0, 0);
            trucks.add(newTruck);
            log.info("Package {} loaded", (Object) packageShape);
        }

        log.info("Import is completed");
        return trucks;
    }

    private List<Truck> loadWithHardMode(List<char[][]> packages) {
        log.info("Start loading trucks with hard mode");
        List<Truck> trucks = new ArrayList<>();
        trucks.add(new Truck());

        for (char[][] packageShape : packages) {
            log.info("Loading package {}", (Object) packageShape);
            boolean placed = false;

            for (Truck truck : trucks) {
                if (tryPlacePackageInTruck(truck, packageShape)) {
                    placed = true;
                    log.info("Package {} loaded", (Object) packageShape);
                    break;
                }
            }

            if (placed) {
                continue;
            }

            Truck newTruck = new Truck();
            if (!tryPlacePackageInTruck(newTruck, packageShape)) {
                continue;
            }

            trucks.add(newTruck);
            log.info("Package {} loaded", (Object) packageShape);
        }

        trucks.removeIf(Truck::isEmpty);
        log.info("Import is completed");
        return trucks;
    }


    private boolean tryPlacePackageInTruck(Truck truck, char[][] packageShape) {
        for (int y = 0; y <= Truck.HEIGHT - packageShape.length; y++) {
            for (int x = 0; x <= Truck.WIDTH - packageShape[0].length; x++) {
                if (truck.canPlace(packageShape, x, y)) {
                    truck.place(packageShape, x, y);
                    return true;
                }
            }
        }
        return false;
    }
}
