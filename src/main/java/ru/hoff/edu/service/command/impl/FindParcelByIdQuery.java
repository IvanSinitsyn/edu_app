package ru.hoff.edu.service.command.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.hoff.edu.domain.Parcel;
import ru.hoff.edu.dto.FindParcelByIdQueryDto;
import ru.hoff.edu.dto.response.FindParcelByIdResponseDto;
import ru.hoff.edu.service.ParcelMapper;
import ru.hoff.edu.service.ParcelService;
import ru.hoff.edu.service.command.Command;

/**
 * Класс, реализующий обработку запроса на поиск посылки (Parcel) по её названию.
 * Использует сервис {@link ParcelService} для поиска посылок.
 */
@Component
@RequiredArgsConstructor
public class FindParcelByIdQuery implements Command<FindParcelByIdResponseDto, FindParcelByIdQueryDto> {

    private final ParcelService parcelService;
    private final ParcelMapper parcelMapper;

    /**
     * Выполняет запрос на поиск посылки.
     *
     * @param queryDto DTO запроса, содержащее данные для поиска посылки.
     * @return Строка с информацией о найденной посылке или список всех посылок, если запрошено "all".
     */
    @Override
    public FindParcelByIdResponseDto execute(FindParcelByIdQueryDto queryDto) {
        Parcel parcel = parcelService.findByName(queryDto.parcelName());
        return new FindParcelByIdResponseDto(parcelMapper.toDto(parcel));
    }
}
