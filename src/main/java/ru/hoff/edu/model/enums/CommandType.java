package ru.hoff.edu.model.enums;

public enum CommandType {
    UPLOAD,
    UNLOAD;

    public static CommandType fromString(String value) {
        try {
            return CommandType.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unknown command type: " + value, e);
        }
    }
}
