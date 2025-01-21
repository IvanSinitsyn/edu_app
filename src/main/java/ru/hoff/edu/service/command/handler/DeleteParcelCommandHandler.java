package ru.hoff.edu.service.command.handler;

import lombok.RequiredArgsConstructor;
import ru.hoff.edu.dto.DeleteParcelCommandDto;
import ru.hoff.edu.service.ParcelService;
import ru.hoff.edu.service.command.Command;

@RequiredArgsConstructor
public class DeleteParcelCommandHandler implements Command<String, DeleteParcelCommandDto> {

    private final ParcelService parcelService;

    @Override
    public String execute(DeleteParcelCommandDto commandDto) {
        parcelService.delete(commandDto.getParcelName());
        return String.format("Посылка %s удалена", commandDto.getParcelName());
    }
}
