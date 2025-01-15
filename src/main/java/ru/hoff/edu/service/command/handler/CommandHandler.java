package ru.hoff.edu.service.command.handler;

public interface CommandHandler<T> {

    T handleCommand(String command);
}
