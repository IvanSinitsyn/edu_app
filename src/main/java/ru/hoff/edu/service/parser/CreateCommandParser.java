package ru.hoff.edu.service.parser;

import ru.hoff.edu.dto.BaseCommandDto;
import ru.hoff.edu.dto.CreateParcelCommandDto;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateCommandParser implements CommandParser {

    private final static String PATTERN = "-name \"(.*?)\" -form \"(.*?)\" -symbol \"(.*?)\"";

    @Override
    public BaseCommandDto parse(String command) {
        Pattern pattern = Pattern.compile(PATTERN);
        Matcher matcher = pattern.matcher(command);

        if (!matcher.find()) {
            throw new IllegalArgumentException("Ошибка в синтаксисе команды /create");
        }

        String name = matcher.group(1);
        String form = matcher.group(2);
        String symbol = matcher.group(3);

        String normalizedForm = form.replace("\\n", "\n");
        return new CreateParcelCommandDto(name, normalizedForm, symbol);
    }
}
