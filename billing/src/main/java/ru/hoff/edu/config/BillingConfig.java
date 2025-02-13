package ru.hoff.edu.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.hoff.edu.model.enums.ChequeType;
import ru.hoff.edu.service.strategy.ChequeCalculator;
import ru.hoff.edu.service.strategy.LoadChequeCalculationStrategy;
import ru.hoff.edu.service.strategy.UnloadChequeCalculationStrategy;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class BillingConfig {

    @Bean
    public ObjectMapper objectWriter() {
        return new ObjectMapper();
    }

    @Bean
    public Map<ChequeType, ChequeCalculator> chequeCalculators(
            LoadChequeCalculationStrategy loadChequeCalculationStrategy,
            UnloadChequeCalculationStrategy unloadChequeCalculationStrategy) {
        Map<ChequeType, ChequeCalculator> chequeCalculators = new HashMap<>();
        chequeCalculators.put(ChequeType.LOAD, loadChequeCalculationStrategy);
        chequeCalculators.put(ChequeType.UNLOAD, unloadChequeCalculationStrategy);
        return chequeCalculators;
    }
}
