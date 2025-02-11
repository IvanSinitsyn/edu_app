package ru.hoff.edu.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.hoff.edu.model.Cheque;
import ru.hoff.edu.model.enums.ChequeType;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class ChequeCalculator {

    @Value("${cheque.load-cost}")
    private int loadCost;

    @Value("${cheque.unload-cost}")
    private int unloadCost;

    public Cheque calculate(long cellsCount, int trucksCount, int parcelsCount, ChequeType chequeType) {
        int cellCost = chequeType == ChequeType.LOAD ? loadCost : unloadCost;
        BigDecimal totalCost = new BigDecimal(cellsCount * cellCost);

        return Cheque.builder()
                .date(LocalDate.now())
                .cost(totalCost)
                .trucksCount(trucksCount)
                .parcelCount(parcelsCount)
                .build();
    }
}
