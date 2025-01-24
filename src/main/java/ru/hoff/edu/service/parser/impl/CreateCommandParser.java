package ru.hoff.edu.service.parser.impl;

import ru.hoff.edu.dto.BaseCommandDto;
import ru.hoff.edu.dto.CreateParcelCommandDto;
import ru.hoff.edu.service.parser.CommandParser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Класс, реализующий парсер для команды создания посылки.
 * Разбирает строку команды и преобразует её в DTO для создания посылки.
 */
public class CreateCommandParser implements CommandParser {

    private final static Pattern pattern = Pattern.compile("--name \"(.*?)\" --form \"(.*?)\" --symbol \"(.*?)\"");

    /**
     * Разбирает строку команды создания посылки и преобразует её в DTO.
     *
     * @param command Строка команды, которую необходимо разобрать.
     * @return Объект {@link CreateParcelCommandDto}, содержащий данные команды.
     * @throws IllegalArgumentException если строка команды не соответствует ожидаемому формату.
     */
    @Override
    public BaseCommandDto parse(String command) {
        Matcher matcher = pattern.matcher(command);

        if (!matcher.find()) {
            throw new IllegalArgumentException("Ошибка в синтаксисе команды /create");
        }

        String name = matcher.group(1);
        String form = matcher.group(2);
        String symbol = matcher.group(3);
        return new CreateParcelCommandDto(name, form, symbol);
    }
}
