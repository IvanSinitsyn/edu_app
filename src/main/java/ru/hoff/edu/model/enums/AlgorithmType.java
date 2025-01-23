package ru.hoff.edu.model.enums;

public enum AlgorithmType {
    EASY,
    EQUALLY,
    OPTIMAL;

    public static AlgorithmType fromString(String value) {
        try {
            return AlgorithmType.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unknown algorithm type: " + value, e);
        }
    }
}
