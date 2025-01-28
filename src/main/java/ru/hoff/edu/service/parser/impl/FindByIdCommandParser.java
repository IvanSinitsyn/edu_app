package ru.hoff.edu.service.parser.impl;

import ru.hoff.edu.dto.BaseCommandDto;
import ru.hoff.edu.dto.FindParcelByIdQueryDto;
import ru.hoff.edu.service.parser.CommandParser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Класс, реализующий парсер для команды поиска посылки по её названию.
 * Разбирает строку команды и преобразует её в DTO для поиска посылки.
 */
public class FindByIdCommandParser implements CommandParser {

    private static final int NAME_GROUP = 1;
    private static final Pattern pattern = Pattern.compile("--name \"(.*?)\"");

    /**
     * Разбирает строку команды поиска посылки и преобразует её в DTO.
     *
     * @param command Строка команды, которую необходимо разобрать.
     * @return Объект {@link FindParcelByIdQueryDto}, содержащий название посылки.
     * @throws IllegalArgumentException если строка команды не соответствует ожидаемому формату.
     */
    @Override
    public BaseCommandDto parse(String command) {
        Matcher matcher = pattern.matcher(command);

        if (!matcher.find()) {
            throw new IllegalArgumentException("Ошибка в синтаксисе команды /find");
        }

        String parcelName = matcher.group(NAME_GROUP);
        return new FindParcelByIdQueryDto(parcelName);
    }
}
