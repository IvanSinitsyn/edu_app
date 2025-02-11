package ru.hoff.edu.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cheque {

    private LocalDate date;

    private String clientId;

    private int trucksCount;

    private int parcelCount;

    private BigDecimal cost;
}
