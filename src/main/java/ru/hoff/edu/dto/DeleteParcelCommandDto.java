package ru.hoff.edu.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeleteParcelCommandDto implements BaseCommandDto {

    private final String parcelName;

    @Override
    public String getCommandType() {
        return "/delete";
    }
}
