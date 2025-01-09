package ru.hoff.edu.domain;

import lombok.Getter;

@Getter
public class Parcel {

    private final String name;
    private final char[][] form;

    public Parcel(String name, char[][] form) {
        this.name = name;
        this.form = form;
    }

    public Integer getWidth() {
        return form[0].length;
    }

    public Integer getHeight() {
        return form.length;
    }
}
