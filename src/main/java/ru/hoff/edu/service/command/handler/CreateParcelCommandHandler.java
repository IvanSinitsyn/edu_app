package ru.hoff.edu.service.command.handler;

import org.springframework.stereotype.Component;
import ru.hoff.edu.domain.Parcel;
import ru.hoff.edu.dto.CreateParcelCommandDto;
import ru.hoff.edu.service.ParcelService;
import ru.hoff.edu.service.command.Command;
import ru.hoff.edu.util.DataConverter;

@Component
public class CreateParcelCommandHandler implements Command<String, CreateParcelCommandDto> {

    private final ParcelService parcelService;

    public CreateParcelCommandHandler(ParcelService parcelService) {
        this.parcelService = parcelService;
    }

    @Override
    public String execute(CreateParcelCommandDto commandDto) {
        Parcel parcel = new Parcel(
                commandDto.getName(),
                DataConverter.convertStringToForm(commandDto.getForm()),
                commandDto.getSymbol(),
                false);
        parcelService.add(parcel);
        return parcel.showInfo();
    }
}
