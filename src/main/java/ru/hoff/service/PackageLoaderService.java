package ru.hoff.service;

import lombok.extern.slf4j.Slf4j;
import ru.hoff.domain.Truck;
import ru.hoff.enums.Mode;
import ru.hoff.util.TxtParser;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class PackageLoaderService {

    private final TxtParser txtParser;

    public PackageLoaderService(TxtParser txtParser) {
        this.txtParser = txtParser;
    }

    public List<Truck> loadPackages(String inputFile, Mode mode) {
        List<char[][]> packages = txtParser.parsePackageFromFile(inputFile);

        if (packages.isEmpty()) {
            log.info("No packages found");
            return new ArrayList<>();
        }

        List<Truck> trucks = new ArrayList<>();
        if (mode == Mode.EASY) {
            return loadWithEasyMode(trucks, packages);
        } else {
            return loadWithHardMode(trucks, packages);
        }
    }

    private List<Truck> loadWithEasyMode(List<Truck> trucks, List<char[][]> packages) {
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

    private List<Truck> loadWithHardMode(List<Truck> trucks, List<char[][]> packages) {
        log.info("Start loading trucks with hard mode");
        if (trucks.isEmpty()) {
            trucks.add(new Truck());
        }

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
