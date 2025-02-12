package ru.hoff.edu.service.parser.impl;

import org.springframework.stereotype.Component;
import ru.hoff.edu.service.mediator.request.Request;
import ru.hoff.edu.service.mediator.request.impl.FindParcelByIdRequest;
import ru.hoff.edu.service.parser.CommandParser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Класс, реализующий парсер для команды поиска посылки по её названию.
 * Разбирает строку команды и преобразует её в DTO для поиска посылки.
 */
@Component
public class FindByIdCommandParser implements CommandParser {

    private static final int NAME_GROUP = 1;
    private static final Pattern pattern = Pattern.compile("--name \"(.*?)\"");

    /**
     * Разбирает строку команды поиска посылки и преобразует её в DTO.
     *
     * @param command Строка команды, которую необходимо разобрать.
     * @return Объект {@link FindParcelByIdRequest}, содержащий название посылки.
     * @throws IllegalArgumentException если строка команды не соответствует ожидаемому формату.
     */
    @Override
    public Request parse(String command) {
        Matcher matcher = pattern.matcher(command);

        if (!matcher.find()) {
            throw new IllegalArgumentException("Ошибка в синтаксисе команды /find");
        }

        String parcelName = matcher.group(NAME_GROUP);
        return new FindParcelByIdRequest(parcelName);
    }
}
