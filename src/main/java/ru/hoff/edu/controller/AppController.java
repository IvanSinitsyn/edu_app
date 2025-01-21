package ru.hoff.edu.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
@RequiredArgsConstructor
public class AppController {

    private final ApplicationContext applicationContext;

    @ShellMethod(key = "/exit")
    public void exit() {
        System.exit(SpringApplication.exit(applicationContext, () -> 0));
    }
}
