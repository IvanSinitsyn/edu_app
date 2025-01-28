package ru.hoff.edu.service.command.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.hoff.edu.domain.Parcel;
import ru.hoff.edu.dto.CreateParcelCommandDto;
import ru.hoff.edu.dto.response.CreateParcelResponseDto;
import ru.hoff.edu.service.ParcelMapper;
import ru.hoff.edu.service.ParcelService;
import ru.hoff.edu.service.command.Command;
import ru.hoff.edu.util.DataConverter;

/**
 * Класс, реализующий обработку команды создания посылки (Parcel).
 * Использует сервис {@link ParcelService} для добавления новой посылки.
 */
@Component
@RequiredArgsConstructor
public class CreateParcelCommand implements Command<CreateParcelResponseDto, CreateParcelCommandDto> {

    private final ParcelService parcelService;
    private final ParcelMapper parcelMapper;

    /**
     * Выполняет команду создания посылки.
     *
     * @param commandDto DTO команды, содержащее данные для создания посылки.
     * @return Строка с информацией о созданной посылке.
     */
    @Override
    public CreateParcelResponseDto execute(CreateParcelCommandDto commandDto) {
        Parcel parcel = Parcel.builder()
                .name(commandDto.name())
                .form(DataConverter.convertStringToForm(commandDto.form()))
                .symbol(commandDto.symbol())
                .build();
        parcelService.add(parcel);
        return new CreateParcelResponseDto(parcelMapper.toDto(parcel));
    }
}
