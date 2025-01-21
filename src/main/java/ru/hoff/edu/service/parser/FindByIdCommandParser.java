package ru.hoff.edu.service.parser;

import ru.hoff.edu.dto.BaseCommandDto;
import ru.hoff.edu.dto.FindParcelByIdQueryDto;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FindByIdCommandParser implements CommandParser {

    private final static String PATTERN = "\"(.*?)\"";

    @Override
    public BaseCommandDto parse(String command) {
        Pattern pattern = Pattern.compile(PATTERN);
        Matcher matcher = pattern.matcher(command);

        if (!matcher.find()) {
            throw new IllegalArgumentException("Ошибка в синтаксисе команды /find");
        }

        String parcelName = matcher.group(1);
        return new FindParcelByIdQueryDto(parcelName);
    }
}
