package ru.hoff.edu.service.parser;

import org.springframework.stereotype.Component;
import ru.hoff.edu.model.dto.request.FindAllParcelsRequestDto;
import ru.hoff.edu.service.Request;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Класс, реализующий парсер для команды поиска посылок.
 * Разбирает строку команды и преобразует её в DTO для поиска посылок.
 */
@Component
public class FindAllCommandParser implements CommandParser {

    private static final int PAGE_GROUP = 1;
    private static final int SIZE_GROUP = 2;
    private static final Pattern pattern = Pattern.compile("--page \"(.*?)\" --size \"(.*?)\"");

    /**
     * Разбирает строку команды поиска посылок и преобразует её в DTO.
     *
     * @param command Строка команды, которую необходимо разобрать.
     * @return Объект {@link FindAllParcelsRequestDto}, содержащий параметры поиска посылок.
     * @throws IllegalArgumentException если строка команды не соответствует ожидаемому формату.
     */
    @Override
    public Request parse(String command) {
        Matcher matcher = pattern.matcher(command);

        if (!matcher.find()) {
            throw new IllegalArgumentException("Ошибка в синтаксисе команды /find");
        }

        String page = matcher.group(PAGE_GROUP);
        String size = matcher.group(SIZE_GROUP);
        return new FindAllParcelsRequestDto(Integer.parseInt(page), Integer.parseInt(size));
    }
}