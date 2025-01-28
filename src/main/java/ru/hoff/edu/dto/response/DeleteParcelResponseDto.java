package ru.hoff.edu.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class DeleteParcelResponseDto implements BaseResponseDto {

    private final String result;
}
