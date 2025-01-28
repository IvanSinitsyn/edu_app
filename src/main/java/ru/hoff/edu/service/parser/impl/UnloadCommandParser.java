package ru.hoff.edu.service.parser.impl;

import ru.hoff.edu.dto.BaseCommandDto;
import ru.hoff.edu.dto.UnloadParcelsCommandDto;
import ru.hoff.edu.service.parser.CommandParser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Класс, реализующий парсер для команды выгрузки данных о посылках.
 * Разбирает строку команды и преобразует её в DTO для выгрузки данных.
 */
public class UnloadCommandParser implements CommandParser {

    private static final int IN_FILE_GROUP = 1;
    private static final int OUT_FILE_GROUP = 2;
    private static final Pattern pattern = Pattern.compile("--infile \"(.*?)\" --outfile \"(.*?)\"(?: --withcount)?");

    /**
     * Разбирает строку команды выгрузки данных и преобразует её в DTO.
     *
     * @param command Строка команды, которую необходимо разобрать.
     * @return Объект {@link UnloadParcelsCommandDto}, содержащий данные для выгрузки.
     * @throws IllegalArgumentException если строка команды не соответствует ожидаемому формату.
     */
    @Override
    public BaseCommandDto parse(String command) {
        Matcher matcher = pattern.matcher(command);

        if (!matcher.find()) {
            throw new IllegalArgumentException("Ошибка в синтаксисе команды unload");
        }

        String inFileName = matcher.group(IN_FILE_GROUP);
        String outFileName = matcher.group(OUT_FILE_GROUP);
        boolean withCount = command.contains("--withcount");

        return new UnloadParcelsCommandDto(inFileName, outFileName, withCount);
    }
}
