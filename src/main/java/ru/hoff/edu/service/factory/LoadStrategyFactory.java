package ru.hoff.edu.service.factory;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.hoff.edu.model.enums.AlgorithmType;
import ru.hoff.edu.service.strategy.LoadStrategy;

import java.util.Map;

/**
 * Фабрика для создания стратегий загрузки посылок в грузовики.
 * В зависимости от типа алгоритма создает соответствующую стратегию загрузки.
 */
@Component
@RequiredArgsConstructor
public class LoadStrategyFactory {

    private final Map<AlgorithmType, LoadStrategy> loadStrategies;

    /**
     * Создает стратегию загрузки на основе переданного типа алгоритма.
     *
     * @param algorithmType Тип алгоритма загрузки.
     * @return Стратегия загрузки, соответствующая переданному типу алгоритма.
     */
    public LoadStrategy createStrategy(AlgorithmType algorithmType) {
        return loadStrategies.get(algorithmType);
    }
}
