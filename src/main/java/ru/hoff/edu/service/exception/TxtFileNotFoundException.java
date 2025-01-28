package ru.hoff.edu.service.exception;

public class TxtFileNotFoundException extends RuntimeException {

    public TxtFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
