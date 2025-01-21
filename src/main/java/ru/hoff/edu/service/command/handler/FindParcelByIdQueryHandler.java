package ru.hoff.edu.service.command.handler;

import org.springframework.stereotype.Component;
import ru.hoff.edu.domain.Parcel;
import ru.hoff.edu.dto.FindParcelByIdQueryDto;
import ru.hoff.edu.service.ParcelService;
import ru.hoff.edu.service.command.Command;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class FindParcelByIdQueryHandler implements Command<String, FindParcelByIdQueryDto> {

    private static final String FIND_ALL_PARCEL_COMMAND = "all";
    private final ParcelService parcelService;

    public FindParcelByIdQueryHandler(ParcelService parcelService) {
        this.parcelService = parcelService;
    }

    @Override
    public String execute(FindParcelByIdQueryDto queryDto) {
        if (!FIND_ALL_PARCEL_COMMAND.equals(queryDto.getParcelName())) {
            Optional<Parcel> parcel = parcelService.findByName(queryDto.getParcelName());
            if (parcel.isPresent()) {
                return parcel.get().showInfo();
            }

            return "Посылка не найдена";
        }

        List<Parcel> parcels = parcelService.findAll();
        List<String> parcelInfo = new ArrayList<>();
        for (Parcel parcel : parcels) {
            parcelInfo.add(parcel.showInfo());
        }
        return String.join("\n", parcelInfo);
    }
}
