package ru.hoff.edu.service.command.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import ru.hoff.edu.domain.Parcel;
import ru.hoff.edu.dto.FindAllParcelsQueryDto;
import ru.hoff.edu.dto.response.FindAllParcelsResponseDto;
import ru.hoff.edu.service.ParcelMapper;
import ru.hoff.edu.service.ParcelService;
import ru.hoff.edu.service.command.Command;

/**
 * Класс, реализующий обработку запроса на поиск всех посылок (Parcel).
 * Использует сервис {@link ParcelService} для поиска посылок.
 */
@Component
@RequiredArgsConstructor
public class FindAllParcelsQuery implements Command<FindAllParcelsResponseDto, FindAllParcelsQueryDto> {

    private final ParcelService parcelService;
    private final ParcelMapper parcelMapper;

    @Override
    public FindAllParcelsResponseDto execute(FindAllParcelsQueryDto commandDto) {
        Page<Parcel> parcels = parcelService.findAll(commandDto.page(), commandDto.size());
        return new FindAllParcelsResponseDto(parcelMapper.toDtoList(parcels.getContent()), parcels.getTotalPages(), parcels.getTotalElements());
    }
}
