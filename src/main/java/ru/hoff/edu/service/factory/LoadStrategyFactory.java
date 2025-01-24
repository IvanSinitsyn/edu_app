package ru.hoff.edu.service.factory;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.hoff.edu.model.enums.AlgorithmType;
import ru.hoff.edu.service.ParcelService;
import ru.hoff.edu.service.strategy.LoadStrategy;
import ru.hoff.edu.service.strategy.impl.EasyLoadStrategy;
import ru.hoff.edu.service.strategy.impl.EqualLoadStrategy;
import ru.hoff.edu.service.strategy.impl.OptimalLoadStrategy;

/**
 * Фабрика для создания стратегий загрузки посылок в грузовики.
 * В зависимости от типа алгоритма создает соответствующую стратегию загрузки.
 */
@Component
@RequiredArgsConstructor
public class LoadStrategyFactory {

    private final ParcelService parcelService;

    /**
     * Создает стратегию загрузки на основе переданного типа алгоритма.
     *
     * @param algorithmType Тип алгоритма загрузки.
     * @return Стратегия загрузки, соответствующая переданному типу алгоритма.
     */
    public LoadStrategy createStrategy(AlgorithmType algorithmType) {
        return switch (algorithmType) {
            case EASY -> new EasyLoadStrategy(parcelService);
            case EQUALLY -> new EqualLoadStrategy(parcelService);
            case OPTIMAL -> new OptimalLoadStrategy(parcelService);
        };
    }
}
