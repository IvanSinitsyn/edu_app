package ru.hoff.edu.dto;

import lombok.Getter;

@Getter
public class FindParcelByIdQueryDto implements BaseCommandDto {

    private final String parcelName;

    public FindParcelByIdQueryDto(String parcelName) {
        this.parcelName = parcelName;
    }

    @Override
    public String getCommandType() {
        return "/find";
    }
}
