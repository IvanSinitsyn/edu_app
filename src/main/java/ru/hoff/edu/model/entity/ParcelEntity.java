package ru.hoff.edu.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
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

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }
}
