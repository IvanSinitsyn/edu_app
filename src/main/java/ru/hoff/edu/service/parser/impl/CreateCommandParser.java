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

    private static final int NAME_GROUP = 1;
    private static final int FORM_GROUP = 2;
    private static final int SYMBOL_GROUP = 3;
    private static final Pattern pattern = Pattern.compile("--name \"(.*?)\" --form \"(.*?)\" --symbol \"(.*?)\"");

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

        String name = matcher.group(NAME_GROUP);
        String form = matcher.group(FORM_GROUP);
        String symbol = matcher.group(SYMBOL_GROUP);
        return new CreateParcelCommandDto(name, form, symbol);
    }
}
