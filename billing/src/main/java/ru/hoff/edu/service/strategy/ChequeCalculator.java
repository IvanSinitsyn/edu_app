package ru.hoff.edu.service.strategy;

import org.springframework.stereotype.Component;
import ru.hoff.edu.model.Cheque;

@Component
public interface ChequeCalculator {

    Cheque calculate(long cellsCount, int trucksCount, int parcelsCount);
}
