package ru.hoff.edu.service.command.handler;

import ru.hoff.edu.dto.DeleteParcelCommandDto;
import ru.hoff.edu.service.ParcelService;
import ru.hoff.edu.service.command.Command;

public class DeleteParcelCommandHandler implements Command<String, DeleteParcelCommandDto> {

    private final ParcelService parcelService;

    public DeleteParcelCommandHandler(ParcelService parcelService) {
        this.parcelService = parcelService;
    }

    @Override
    public String execute(DeleteParcelCommandDto commandDto) {
        parcelService.delete(commandDto.getParcelName());
        return String.format("Посылка %s удалена", commandDto.getParcelName());
    }
}
