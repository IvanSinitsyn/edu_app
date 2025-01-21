package ru.hoff.edu.service.command.handler;

import ru.hoff.edu.domain.Parcel;
import ru.hoff.edu.dto.CreateParcelCommandDto;
import ru.hoff.edu.service.ParcelService;
import ru.hoff.edu.service.command.Command;
import ru.hoff.edu.util.DataConverter;

public class CreateParcelCommandHandler implements Command<String, CreateParcelCommandDto> {

    private final ParcelService parcelService;

    public CreateParcelCommandHandler(ParcelService parcelService) {
        this.parcelService = parcelService;
    }

    @Override
    public String execute(CreateParcelCommandDto commandDto) {
        Parcel parcel = Parcel.builder()
                .name(commandDto.getName())
                .form(DataConverter.convertStringToForm(commandDto.getForm()))
                .symbol(commandDto.getSymbol())
                .build();
        parcelService.add(parcel);
        return parcel.showInfo();
    }
}
