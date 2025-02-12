package ru.hoff.edu.service.strategy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.hoff.edu.model.Cheque;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class LoadChequeCalculationStrategy implements ChequeCalculator {

    @Value("${cheque.load-cost}")
    private int loadCost;

    public Cheque calculate(long cellsCount, int trucksCount, int parcelsCount) {
        BigDecimal totalCost = new BigDecimal(cellsCount * loadCost);

        return Cheque.builder()
                .date(LocalDate.now())
                .cost(totalCost)
                .trucksCount(trucksCount)
                .parcelCount(parcelsCount)
                .build();
    }
}
