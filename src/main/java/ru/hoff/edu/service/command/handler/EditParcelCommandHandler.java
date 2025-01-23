package ru.hoff.edu.service.command.handler;

import lombok.RequiredArgsConstructor;
import ru.hoff.edu.domain.Parcel;
import ru.hoff.edu.dto.EditParcelCommandDto;
import ru.hoff.edu.service.ParcelService;
import ru.hoff.edu.service.command.Command;
import ru.hoff.edu.util.DataConverter;

@RequiredArgsConstructor
public class EditParcelCommandHandler implements Command<String, EditParcelCommandDto> {

    private final ParcelService parcelService;

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
