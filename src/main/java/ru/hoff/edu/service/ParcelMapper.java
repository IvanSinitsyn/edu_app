package ru.hoff.edu.service;

import org.springframework.stereotype.Component;
import ru.hoff.edu.domain.Parcel;
import ru.hoff.edu.dto.ParcelDto;
import ru.hoff.edu.entity.ParcelEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static ru.hoff.edu.util.DataConverter.convertArrayToString;
import static ru.hoff.edu.util.DataConverter.convertStringToForm;

@Component
public class ParcelMapper {

    public ParcelDto toDto(Parcel parcel) {
        if (parcel == null) {
            return null;
        }

        char[][] formCopy = Arrays.stream(parcel.getForm())
                .map(char[]::clone)
                .toArray(char[][]::new);

        return new ParcelDto(parcel.getName(), formCopy, parcel.getSymbol(), parcel.isLoaded());
    }

    public List<ParcelDto> toDtoList(List<Parcel> parcels) {
        if (parcels == null) {
            return new ArrayList<>();
        }

        List<ParcelDto> parcelDtos = new ArrayList<>();
        for (Parcel parcel : parcels) {
            parcelDtos.add(toDto(parcel));
        }

        return parcelDtos;
    }

    public ParcelEntity toEntity(Parcel parcel) {
        if (parcel == null) {
            return null;
        }

        char[][] formCopy = Arrays.stream(parcel.getForm())
                .map(char[]::clone)
                .toArray(char[][]::new);

        return new ParcelEntity(parcel.getName(), convertArrayToString(formCopy), parcel.getSymbol(), parcel.isLoaded());
    }

    public Parcel fromEntity(ParcelEntity parcelEntity) {
        if (parcelEntity == null) {
            return null;
        }

        char[][] formCopy = convertStringToForm(parcelEntity.getForm());

        return new Parcel(parcelEntity.getName(), formCopy, parcelEntity.getSymbol(), parcelEntity.isLoaded());
    }

    public List<Parcel> fromEntityList(List<ParcelEntity> parcelEntities) {
        if (parcelEntities == null) {
            return new ArrayList<>();
        }

        List<Parcel> parcels = new ArrayList<>();
        for (ParcelEntity parcelEntity : parcelEntities) {
            parcels.add(fromEntity(parcelEntity));
        }
        return parcels;
    }
}
