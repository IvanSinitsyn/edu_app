package ru.hoff.edu.service.strategy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.hoff.edu.model.Cheque;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class UnloadChequeCalculationStrategy implements ChequeCalculator {

    @Value("${cheque.unload-cost}")
    private int unloadCost;

    @Override
    public Cheque calculate(long cellsCount, int trucksCount, int parcelsCount) {
        BigDecimal totalCost = new BigDecimal(cellsCount * unloadCost);

        return Cheque.builder()
                .date(LocalDate.now())
                .cost(totalCost)
                .trucksCount(trucksCount)
                .parcelCount(parcelsCount)
                .build();
    }
}
