package ru.hoff.edu.service.parser.impl;

import ru.hoff.edu.dto.BaseCommandDto;
import ru.hoff.edu.dto.EditParcelCommandDto;
import ru.hoff.edu.service.parser.CommandParser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Класс, реализующий парсер для команды редактирования посылки.
 * Разбирает строку команды и преобразует её в DTO для редактирования посылки.
 */
public class EditCommandParser implements CommandParser {

    private static final int ID_GROUP = 1;
    private static final int NAME_GROUP = 2;
    private static final int FORM_GROUP = 3;
    private static final int SYMBOL_GROUP = 4;
    private static final Pattern pattern = Pattern.compile("--id \"(.*?)\" --name \"(.*?)\" --form \"(.*?)\" --symbol \"(.*?)\"");

    /**
     * Разбирает строку команды редактирования посылки и преобразует её в DTO.
     *
     * @param command Строка команды, которую необходимо разобрать.
     * @return Объект {@link EditParcelCommandDto}, содержащий данные для редактирования посылки.
     * @throws IllegalArgumentException если строка команды не соответствует ожидаемому формату.
     */
    @Override
    public BaseCommandDto parse(String command) {
        Matcher matcher = pattern.matcher(command);

        if (!matcher.find()) {
            throw new IllegalArgumentException("Ошибка в синтаксисе команды /edit");
        }

        String id = matcher.group(ID_GROUP);
        String name = matcher.group(NAME_GROUP);
        String form = matcher.group(FORM_GROUP);
        String symbol = matcher.group(SYMBOL_GROUP);

        String normalizedForm = form.replace("\\n", "\n");
        return new EditParcelCommandDto(id, name, normalizedForm, symbol);
    }
}
