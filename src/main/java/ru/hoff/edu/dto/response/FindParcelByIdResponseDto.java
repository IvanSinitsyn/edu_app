package ru.hoff.edu.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.hoff.edu.dto.ParcelDto;

@Getter
@RequiredArgsConstructor
public class FindParcelByIdResponseDto implements BaseResponseDto {

    private final ParcelDto parcel;
}
