package ru.hoff.edu.service.parser;

import ru.hoff.edu.dto.BaseCommandDto;
import ru.hoff.edu.dto.UnloadParcelsCommandDto;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UnloadCommandParser implements CommandParser {

    private final static String PATTERN = "unload -infile \"(.*?)\" -outfile \"(.*?)\"(?: --withcount)?";

    @Override
    public BaseCommandDto parse(String command) {
        Pattern pattern = Pattern.compile(PATTERN);

        Matcher matcher = pattern.matcher(command);

        if (!matcher.find()) {
            throw new IllegalArgumentException("Ошибка в синтаксисе команды unload");
        }

        String inFileName = matcher.group(1);
        String outFileName = matcher.group(2);
        boolean withCount = command.contains("--withcount");

        return new UnloadParcelsCommandDto(inFileName, outFileName, withCount);
    }
}
