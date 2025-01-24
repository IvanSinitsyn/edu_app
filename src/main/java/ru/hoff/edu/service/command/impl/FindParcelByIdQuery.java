package ru.hoff.edu.service.command.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.hoff.edu.domain.Parcel;
import ru.hoff.edu.dto.FindParcelByIdQueryDto;
import ru.hoff.edu.service.ParcelService;
import ru.hoff.edu.service.command.Command;

import java.util.stream.Collectors;

/**
 * Класс, реализующий обработку запроса на поиск посылки (Parcel) по её названию.
 * Если название посылки равно "all", возвращает информацию о всех посылках.
 * Использует сервис {@link ParcelService} для поиска посылок.
 */
@Component
@RequiredArgsConstructor
public class FindParcelByIdQuery implements Command<String, FindParcelByIdQueryDto> {

    private static final String FIND_ALL_PARCEL_COMMAND = "all";
    private final ParcelService parcelService;

    /**
     * Выполняет запрос на поиск посылки.
     *
     * @param queryDto DTO запроса, содержащее данные для поиска посылки.
     * @return Строка с информацией о найденной посылке или список всех посылок, если запрошено "all".
     */
    @Override
    public String execute(FindParcelByIdQueryDto queryDto) {
        if (!FIND_ALL_PARCEL_COMMAND.equals(queryDto.getParcelName())) {
            return parcelService.findByName(queryDto.getParcelName())
                    .map(Parcel::showInfo)
                    .orElse("Посылка не найдена");
        }

        return parcelService.findAll()
                .stream()
                .map(Parcel::showInfo)
                .collect(Collectors.joining("\n"));
    }
}
