package ru.hoff.edu.service;

import java.util.regex.*;

public class CommandService {

    public String handleCommand(String command) {
        if (command.startsWith("/create")) {
            return handleCreate(command);
        } else if (command.startsWith("/find")) {
            return handleFind(command);
        } else if (command.startsWith("/edit")) {
            return handleEdit(command);
        } else if (command.startsWith("/delete")) {
            return handleDelete(command);
        } else if (command.startsWith("/load")) {
            return handleLoad(command);
        } else if (command.startsWith("/unload")) {
            return handleUnload(command);
        } else {
            return "Неизвестная команда. Попробуйте: /create, /find, /edit, /delete, /load, /unload";
        }
    }

    private String handleCreate(String command) {
        // Пример: /create -name "Квадратное колесо" -form "xxx\nx x\nxxx" -symbol "o"
        Pattern pattern = Pattern.compile("-name \"(.*?)\" -form \"(.*?)\" -symbol \"(.*?)\"");
        Matcher matcher = pattern.matcher(command);

        if (matcher.find()) {
            String name = matcher.group(1);
            String form = matcher.group(2);
            String symbol = matcher.group(3);

            // Вызов логики для создания посылки
            return "id(name): \"" + name + "\"\nform:\n" + form.replace(" ", "").replace("\n", "\n");
        }
        return "Ошибка в синтаксисе команды /create";
    }

    private String handleFind(String command) {
        // Пример: /find "Посылка Тип 4"
        Pattern pattern = Pattern.compile("\"(.*?)\"");
        Matcher matcher = pattern.matcher(command);

        if (matcher.find()) {
            String parcelName = matcher.group(1);
            // Логика поиска
            return "id(name): \"" + parcelName + "\"\nform:\n44\n44"; // Заглушка
        }
        return "Ошибка в синтаксисе команды /find";
    }

    private String handleEdit(String command) {
        // Аналогичная логика разбора и вызова методов сервиса
        return "Команда /edit в процессе разработки";
    }

    private String handleDelete(String command) {
        // Аналогичная логика разбора и вызова методов сервиса
        return "Команда /delete в процессе разработки";
    }

    private String handleLoad(String command) {
        // Аналогичная логика разбора и вызова методов сервиса
        return "Команда /load в процессе разработки";
    }

    private String handleUnload(String command) {
        // Аналогичная логика разбора и вызова методов сервиса
        return "Команда /unload в процессе разработки";
    }
}

