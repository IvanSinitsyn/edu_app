package ru.hoff.edu.service.command.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.hoff.edu.domain.Parcel;
import ru.hoff.edu.dto.EditParcelCommandDto;
import ru.hoff.edu.dto.response.EditParcelResponseDto;
import ru.hoff.edu.service.ParcelMapper;
import ru.hoff.edu.service.ParcelService;
import ru.hoff.edu.service.command.Command;
import ru.hoff.edu.util.DataConverter;

/**
 * Класс, реализующий обработку команды редактирования посылки (Parcel).
 * Использует сервис {@link ParcelService} для изменения данных существующей посылки.
 */
@Component
@RequiredArgsConstructor
public class EditParcelCommand implements Command<EditParcelResponseDto, EditParcelCommandDto> {

    private final ParcelService parcelService;
    private final ParcelMapper parcelMapper;

    /**
     * Выполняет команду редактирования посылки.
     *
     * @param commandDto DTO команды, содержащее данные для редактирования посылки.
     * @return Строка с информацией о отредактированной посылке.
     */
    @Override
    public EditParcelResponseDto execute(EditParcelCommandDto commandDto) {
        Parcel parcel = parcelService.edit(
                commandDto.id(),
                commandDto.name(),
                DataConverter.convertStringToForm(commandDto.form()),
                commandDto.symbol());

        return new EditParcelResponseDto(parcelMapper.toDto(parcel));
    }
}
