package ru.hoff.edu.service.factory;

import lombok.RequiredArgsConstructor;
import ru.hoff.edu.model.enums.AlgorithmType;
import ru.hoff.edu.service.ParcelService;
import ru.hoff.edu.service.strategy.EasyLoadStrategy;
import ru.hoff.edu.service.strategy.EqualLoadStrategy;
import ru.hoff.edu.service.strategy.LoadStrategy;
import ru.hoff.edu.service.strategy.OptimalLoadStrategy;

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
