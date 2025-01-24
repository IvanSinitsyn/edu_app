package ru.hoff.edu.service.command.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.hoff.edu.dto.DeleteParcelCommandDto;
import ru.hoff.edu.service.ParcelService;
import ru.hoff.edu.service.command.Command;

/**
 * Класс, реализующий обработку команды удаления посылки (Parcel).
 * Использует сервис {@link ParcelService} для удаления посылки по её названию.
 */
@Component
@RequiredArgsConstructor
public class DeleteParcelCommand implements Command<String, DeleteParcelCommandDto> {

    private final ParcelService parcelService;

    /**
     * Выполняет команду удаления посылки.
     *
     * @param commandDto DTO команды, содержащее данные для удаления посылки.
     * @return Строка с подтверждением удаления посылки.
     */
    @Override
    public String execute(DeleteParcelCommandDto commandDto) {
        parcelService.delete(commandDto.getParcelName());
        return String.format("Посылка %s удалена", commandDto.getParcelName());
    }
}
