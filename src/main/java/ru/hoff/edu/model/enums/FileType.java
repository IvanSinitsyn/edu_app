package ru.hoff.edu.model.enums;

public enum FileType {
    TXT,
    JSON;

    public static FileType fromString(String value) {
        try {
            return FileType.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unknown result out type: " + value, e);
        }
    }
}
