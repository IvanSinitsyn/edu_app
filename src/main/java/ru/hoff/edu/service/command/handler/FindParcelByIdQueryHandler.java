package ru.hoff.edu.service.command.handler;

import lombok.RequiredArgsConstructor;
import ru.hoff.edu.domain.Parcel;
import ru.hoff.edu.dto.FindParcelByIdQueryDto;
import ru.hoff.edu.service.ParcelService;
import ru.hoff.edu.service.command.Command;

import java.util.stream.Collectors;

@RequiredArgsConstructor
public class FindParcelByIdQueryHandler implements Command<String, FindParcelByIdQueryDto> {

    private static final String FIND_ALL_PARCEL_COMMAND = "all";
    private final ParcelService parcelService;

    @Override
    public String execute(FindParcelByIdQueryDto queryDto) {
        if (!FIND_ALL_PARCEL_COMMAND.equals(queryDto.getParcelName())) {
            return parcelService.findByName(queryDto.getParcelName())
                    .map(Parcel::showInfo)
                    .orElse("Посылка не найдена");
        }

        return parcelService.findAll()
                .stream()
                .map(Parcel::showInfo)
                .collect(Collectors.joining("\n"));
    }
}
