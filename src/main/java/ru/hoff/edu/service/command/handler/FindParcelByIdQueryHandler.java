package ru.hoff.edu.service.command.handler;

import ru.hoff.edu.domain.Parcel;
import ru.hoff.edu.dto.FindParcelByIdQueryDto;
import ru.hoff.edu.service.ParcelService;
import ru.hoff.edu.service.command.Command;

import java.util.ArrayList;
import java.util.List;

public class FindParcelByIdQueryHandler implements Command<String, FindParcelByIdQueryDto> {

    private final String FIND_ALL_PARCEL_COMMAND = "all";
    private final ParcelService parcelService;

    public FindParcelByIdQueryHandler(ParcelService parcelService) {
        this.parcelService = parcelService;
    }

    @Override
    public String execute(FindParcelByIdQueryDto queryDto) {
        try {
            if (!FIND_ALL_PARCEL_COMMAND.equals(queryDto.getParcelName())) {
                Parcel parcel = parcelService.findByName(queryDto.getParcelName());
                return parcel.showInfo();
            }

            List<Parcel> parcels = parcelService.findAll();
            List<String> parcelInfo = new ArrayList<>();
            for (Parcel parcel : parcels) {
                parcelInfo.add(parcel.showInfo());
            }
            return String.join("\n", parcelInfo);
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
