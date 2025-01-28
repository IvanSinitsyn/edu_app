package ru.hoff.edu.service.parser.impl;

import ru.hoff.edu.dto.BaseCommandDto;
import ru.hoff.edu.dto.DeleteParcelCommandDto;
import ru.hoff.edu.service.parser.CommandParser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Класс, реализующий парсер для команды удаления посылки.
 * Разбирает строку команды и преобразует её в DTO для удаления посылки.
 */
public class DeleteCommandParser implements CommandParser {

    private static final int NAME_GROUP = 1;
    private static final Pattern pattern = Pattern.compile("--name \"(.*?)\"");

    /**
     * Разбирает строку команды удаления посылки и преобразует её в DTO.
     *
     * @param command Строка команды, которую необходимо разобрать.
     * @return Объект {@link DeleteParcelCommandDto}, содержащий название посылки.
     * @throws IllegalArgumentException если строка команды не соответствует ожидаемому формату.
     */
    @Override
    public BaseCommandDto parse(String command) {
        Matcher matcher = pattern.matcher(command);

        if (!matcher.find()) {
            throw new IllegalArgumentException("Ошибка в синтаксисе команды /delete");
        }

        String parcelName = matcher.group(NAME_GROUP);
        return new DeleteParcelCommandDto(parcelName);
    }
}
