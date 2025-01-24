package ru.hoff.edu.service.command.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.hoff.edu.domain.Parcel;
import ru.hoff.edu.dto.EditParcelCommandDto;
import ru.hoff.edu.service.ParcelService;
import ru.hoff.edu.service.command.Command;
import ru.hoff.edu.util.DataConverter;

/**
 * Класс, реализующий обработку команды редактирования посылки (Parcel).
 * Использует сервис {@link ParcelService} для изменения данных существующей посылки.
 */
@Component
@RequiredArgsConstructor
public class EditParcelCommand implements Command<String, EditParcelCommandDto> {

    private final ParcelService parcelService;

    /**
     * Выполняет команду редактирования посылки.
     *
     * @param commandDto DTO команды, содержащее данные для редактирования посылки.
     * @return Строка с информацией о отредактированной посылке.
     */
    @Override
    public String execute(EditParcelCommandDto commandDto) {
        Parcel editedParcel = parcelService.edit(
                commandDto.getId(),
                commandDto.getName(),
                DataConverter.convertStringToForm(commandDto.getForm()),
                commandDto.getSymbol());
        return editedParcel.showInfo();
    }
}
