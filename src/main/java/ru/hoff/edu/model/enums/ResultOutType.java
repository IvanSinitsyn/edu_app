package ru.hoff.edu.model.enums;

public enum ResultOutType {
    TEXT,
    FILE;

    public static ResultOutType fromString(String value) {
        try {
            return ResultOutType.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unknown result out type: " + value, e);
        }
    }
}
