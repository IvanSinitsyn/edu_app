package ru.hoff.edu.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
public class Cheque {

    private LocalDate date;

    private String clientId;

    private int trucksCount;

    private int parcelCount;

    private BigDecimal cost;

    @Override
    public String toString() {
        return date + "; Погрузка: " + trucksCount + " машин; " + parcelCount + " посылок; " + cost + " рублей";
    }
}
