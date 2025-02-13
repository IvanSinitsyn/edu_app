package ru.hoff.edu.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import ru.hoff.edu.model.enums.ChequeType;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "cheques")
public class ChequeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String clientId;

    private LocalDate date;

    private int trucksCount;

    private int parcelCount;

    @Enumerated(EnumType.STRING)
    private ChequeType type;

    @Column(precision = 19, scale = 4)
    private BigDecimal cost;
}
