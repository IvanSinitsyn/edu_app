package ru.hoff.edu.service.command.impl;

import org.springframework.stereotype.Component;
import ru.hoff.edu.domain.Parcel;
import ru.hoff.edu.dto.CreateParcelCommandDto;
import ru.hoff.edu.service.ParcelService;
import ru.hoff.edu.service.command.Command;
import ru.hoff.edu.util.DataConverter;

/**
 * Класс, реализующий обработку команды создания посылки (Parcel).
 * Использует сервис {@link ParcelService} для добавления новой посылки.
 */
@Component
public class CreateParcelCommand implements Command<String, CreateParcelCommandDto> {

    private final ParcelService parcelService;

    public CreateParcelCommand(ParcelService parcelService) {
        this.parcelService = parcelService;
    }

    /**
     * Выполняет команду создания посылки.
     *
     * @param commandDto DTO команды, содержащее данные для создания посылки.
     * @return Строка с информацией о созданной посылке.
     */
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
