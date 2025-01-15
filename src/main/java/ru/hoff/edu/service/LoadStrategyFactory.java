package ru.hoff.edu.service;

import lombok.RequiredArgsConstructor;
import ru.hoff.edu.model.enums.AlgorithmType;

@RequiredArgsConstructor
public class LoadStrategyFactory {

    private final ParcelService parcelService;

    public LoadStrategy createStrategy(AlgorithmType algorithmType) {
        return switch (algorithmType) {
            case EASY -> new EasyLoadStrategy(parcelService);
            case EQUALLY -> new EqualLoadStrategy(parcelService);
            case OPTIMAL -> new OptimalLoadStrategy(parcelService);
        };
    }
}
