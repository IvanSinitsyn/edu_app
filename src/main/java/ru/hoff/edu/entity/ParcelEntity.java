package ru.hoff.edu.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@Entity(name = "parcels")
@NoArgsConstructor
public class ParcelEntity {

    @Id
    private String name;

    private String form;

    private String symbol;

    private boolean isLoaded;
}
