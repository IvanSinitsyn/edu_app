package ru.hoff.edu.service.parser;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.hoff.edu.model.dto.request.LoadParcelRequestDto;
import ru.hoff.edu.service.Request;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Класс, реализующий парсер для команды загрузки посылок в грузовики.
 * Разбирает строку команды и преобразует её в DTO для загрузки посылок.
 */
@Component
@RequiredArgsConstructor
public class LoadCommandParser implements CommandParser {

    private static final int USER_ID_GROUP = 2;
    private static final int PARCEL_FILE_GROUP = 3;
    private static final int TRUCKS_GROUP = 4;
    private static final int ALGORITHM_GROUP = 5;
    private static final int OUT_TYPE_GROUP = 6;
    private static final int OUT_FILENAME_GROUP = 7;
    private static final Pattern pattern = Pattern.compile("--user-id \"(.*?)\" --parcels-(text|file) \"(.*?)\" --trucks \"(.*?)\" --algorithm \"(.*?)\" --out (\\w+)(?: --out-filename (\\w+))?");

    /**
     * Разбирает строку команды загрузки посылок и преобразует её в DTO.
     *
     * @param command Строка команды, которую необходимо разобрать.
     * @return Объект {@link LoadParcelRequestDto}, содержащий данные для загрузки посылок.
     * @throws IllegalArgumentException если строка команды не соответствует ожидаемому формату.
     */
    @Override
    public Request parse(String command) {
        Matcher matcher = pattern.matcher(command);

        if (!matcher.find()) {
            throw new IllegalArgumentException("Ошибка в синтаксисе команды load");
        }

        String userId = matcher.group(USER_ID_GROUP);
        String parcelsText = matcher.group(PARCEL_FILE_GROUP);
        String trucksDescriptions = matcher.group(TRUCKS_GROUP);
        String algorithm = matcher.group(ALGORITHM_GROUP);
        String outputFormat = matcher.group(OUT_TYPE_GROUP);
        String outputFilename = matcher.group(OUT_FILENAME_GROUP);

        String normalizedParcelsText = parcelsText.replace("\\n", "\n");
        String normalizedTrucksDescriptions = trucksDescriptions.replace("\\n", "\n");

        return new LoadParcelRequestDto(
                userId,
                algorithm,
                normalizedParcelsText,
                parcelsText,
                normalizedTrucksDescriptions,
                outputFormat,
                outputFilename);
    }
}