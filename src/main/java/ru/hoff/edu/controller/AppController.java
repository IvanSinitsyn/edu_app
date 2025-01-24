package ru.hoff.edu.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

/**
 * Контроллер для обработки команд Spring Shell.
 * <p>
 * Этот класс предоставляет команды, которые могут быть выполнены через Spring Shell.
 * В текущей реализации поддерживается команда для завершения работы приложения.
 * </p>
 */
@ShellComponent
@RequiredArgsConstructor
public class AppController {

    private final ApplicationContext applicationContext;

    /**
     * Завершает работу приложения.
     * <p>
     * Эта команда завершает работу Spring-приложения с кодом выхода 0.
     * </p>
     */
    @ShellMethod(key = "/exit")
    public void exit() {
        System.exit(SpringApplication.exit(applicationContext, () -> 0));
    }
}
