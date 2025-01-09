package ru.hoff.edu.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.hoff.edu.domain.Parcel;
import ru.hoff.edu.domain.Truck;
import ru.hoff.edu.model.Command;
import ru.hoff.edu.model.enums.AlgorithmType;
import ru.hoff.edu.util.DataConverter;
import ru.hoff.edu.util.filewriter.FileWriter;
import ru.hoff.edu.util.InputFileParser;
import ru.hoff.edu.util.filereader.InputFileReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class ParcelUploaderService implements ParcelService {

    private final InputFileReader inputFileReader;
    private final InputFileParser inputFileParser;
    private final FileWriter<Truck> fileWriter;

    @Override
    public void processing(Command command) throws IOException {
        log.info("Start reading inputFile...");

        try {
            List<Map<String, Object>> fileData = inputFileReader.readFile(command.getInputFile());

            log.info("Start parsing packages...");
            List<Parcel> parcels = inputFileParser.parseParcels(fileData);

            if (parcels.isEmpty()) {
                log.info("No packages found");
                return;
            }

            List<Truck> trucks = loadPackagesInTrucks(parcels, command.getAlgorithmType(), command.getMaxTrucksCount());
            makeLoadingReport(trucks);

            String pathToResultFile = command.getPathToResultFile();
            if (pathToResultFile != null && !pathToResultFile.isEmpty()) {
                fileWriter.writeToFile(pathToResultFile, trucks);
            }
        } catch (IOException e) {
            throw new IOException(e);
        }
    }

    public List<Truck> loadPackagesInTrucks(List<Parcel> parcels, AlgorithmType algorithmType, Integer maxTrucksCount) {
        List<Truck> trucks;
        if (algorithmType == AlgorithmType.EASY) {
            trucks = loadWithEasyMode(parcels);
        } else if (algorithmType == AlgorithmType.EQUALLY) {
            trucks = loadWithEquallyMode(parcels);
        } else {
            trucks = loadWithHardMode(parcels);
        }

        if (maxTrucksCount != null && trucks.size() > maxTrucksCount) {
            throw new IllegalArgumentException("Max trucks count is " + maxTrucksCount);
        }

        return trucks;
    }

    public void makeLoadingReport(List<Truck> trucks) {
        for (int i = 0; i < trucks.size(); i++) {
            System.out.println("Truck " + (i + 1) + ":");
            trucks.get(i).showLoadingResult();
        }
    }

    private List<Truck> loadWithEasyMode(List<Parcel> parcels) {
        List<Truck> trucks = new ArrayList<>();
        log.info("Start loading trucks with easy mode");
        for (Parcel parcel : parcels) {
            Truck newTruck = new Truck();
            newTruck.place(parcel, 0, 0);
            trucks.add(newTruck);
            log.info("Package {} loaded", DataConverter.parcelToString(parcel));
        }

        log.info("Import is completed");
        return trucks;
    }

    private List<Truck> loadWithHardMode(List<Parcel> parcels) {
        log.info("Start loading trucks with hard mode");
        List<Truck> trucks = new ArrayList<>();
        for (Parcel parcel : parcels) {
            log.info("Loading package {}", DataConverter.parcelToString(parcel));
            placeParcelInTrucks(trucks, parcel);
            log.info("Package {} loaded", DataConverter.parcelToString(parcel));
        }

        trucks.removeIf(Truck::isEmpty);
        log.info("Import is completed");
        return trucks;
    }

    private List<Truck> loadWithEquallyMode(List<Parcel> parcels) {
        log.info("Start loading trucks with equally mode");
        List<Truck> trucks = new ArrayList<>();

        for (Parcel parcel : parcels) {
            log.info("Loading package {}", DataConverter.parcelToString(parcel));

            Truck suitableTruck = findSuitableTruck(trucks, parcel);
            if (suitableTruck != null) {
                placeParcelInTruck(suitableTruck, parcel);
            } else {
                Truck newTruck = new Truck();
                trucks.add(newTruck);
                placeParcelInTruck(newTruck, parcel);
            }

            log.info("Package {} loaded", DataConverter.parcelToString(parcel));
        }

        trucks.removeIf(Truck::isEmpty);
        log.info("Import is completed");
        return trucks;
    }

    private Truck findSuitableTruck(List<Truck> trucks, Parcel parcel) {
        for (Truck truck : trucks) {
            if (canFitInHalfTruck(truck, parcel)) {
                return truck;
            }
        }
        return null;
    }

    private boolean placeParcelInTrucks(List<Truck> trucks, Parcel parcel) {
        if (trucks.isEmpty()) {
            trucks.add(new Truck());
        }

        for (Truck truck : trucks) {
            if (tryPlacePackageInTruck(truck, parcel)) {
                log.info("Package {} loaded", DataConverter.parcelToString(parcel));
                return true;
            }
        }

        trucks.add(new Truck());
        return placeParcelInTrucks(trucks, parcel);
    }


    private boolean tryPlacePackageInTruck(Truck truck, Parcel parcel) {
        for (int y = 0; y <= Truck.HEIGHT - parcel.getHeight(); y++) {
            for (int x = 0; x <= Truck.WIDTH - parcel.getWidth(); x++) {
                if (truck.canPlace(parcel, x, y)) {
                    truck.place(parcel, x, y);
                    return true;
                }
            }
        }
        return false;
    }

    private boolean canFitInHalfTruck(Truck truck, Parcel parcel) {
        int potentialNewLoad = truck.getCurrentLoad() + parcel.getWidth() * parcel.getHeight();
        return potentialNewLoad <= truck.getHalfCapacity() && tryPlacePackageInTruck(truck, parcel);
    }

    private void placeParcelInTruck(Truck truck, Parcel parcel) {
        for (int y = 0; y <= Truck.HEIGHT - parcel.getHeight(); y++) {
            for (int x = 0; x <= Truck.WIDTH - parcel.getWidth(); x++) {
                if (truck.canPlace(parcel, x, y)) {
                    truck.place(parcel, x, y);
                    return;
                }
            }
        }
    }
}
