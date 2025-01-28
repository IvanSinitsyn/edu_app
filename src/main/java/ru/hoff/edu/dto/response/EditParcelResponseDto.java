package ru.hoff.edu.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.hoff.edu.dto.ParcelDto;

@Setter
@Getter
@RequiredArgsConstructor
public class EditParcelResponseDto implements BaseResponseDto {

    private final ParcelDto parcel;
}
