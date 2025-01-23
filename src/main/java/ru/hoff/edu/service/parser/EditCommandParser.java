package ru.hoff.edu.service.parser;

import ru.hoff.edu.dto.BaseCommandDto;
import ru.hoff.edu.dto.EditParcelCommandDto;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditCommandParser implements CommandParser {

    private final static String PATTERN = "-id \"(.*?)\" -name \"(.*?)\" -form \"(.*?)\" -symbol \"(.*?)\"";

    @Override
    public BaseCommandDto parse(String command) {
        Pattern pattern = Pattern.compile(PATTERN);
        Matcher matcher = pattern.matcher(command);

        if (!matcher.find()) {
            throw new IllegalArgumentException("Ошибка в синтаксисе команды /edit");
        }

        String id = matcher.group(1);
        String name = matcher.group(2);
        String form = matcher.group(3);
        String symbol = matcher.group(4);

        String normalizedForm = form.replace("\\n", "\n");
        return new EditParcelCommandDto(id, name, normalizedForm, symbol);
    }
}
