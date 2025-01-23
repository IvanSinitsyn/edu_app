package ru.hoff.edu.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class FindParcelByIdQueryDto implements BaseCommandDto {

    private final String parcelName;

    @Override
    public String getCommandType() {
        return "/find";
    }
}
