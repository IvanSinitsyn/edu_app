package ru.hoff.edu.service.parser;

import ru.hoff.edu.dto.BaseCommandDto;
import ru.hoff.edu.dto.DeleteParcelCommandDto;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeleteCommandParser implements CommandParser {

    private final static String PATTERN = "\"(.*?)\"";

    @Override
    public BaseCommandDto parse(String command) {
        Pattern pattern = Pattern.compile(PATTERN);
        Matcher matcher = pattern.matcher(command);

        if (!matcher.find()) {
            throw new IllegalArgumentException("Ошибка в синтаксисе команды /delete");
        }

        String parcelName = matcher.group(1);
        return new DeleteParcelCommandDto(parcelName);
    }
}
