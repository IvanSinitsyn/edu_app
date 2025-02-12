package ru.hoff.edu.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Accessors(chain = true)
@Setter
@AllArgsConstructor
@Entity(name = "parcels")
@NoArgsConstructor
public class ParcelEntity {

    @Id
    private String name;

    @Column(columnDefinition = "TEXT")
    private String form;

    private String symbol;

    private boolean isLoaded;
}
