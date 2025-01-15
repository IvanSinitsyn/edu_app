package ru.hoff.edu.dto;

import lombok.Getter;

@Getter
public class DeleteParcelCommandDto implements BaseCommandDto {

    private final String parcelName;

    public DeleteParcelCommandDto(String parcelName) {
        this.parcelName = parcelName;
    }

    @Override
    public String getCommandType() {
        return "/delete";
    }
}
