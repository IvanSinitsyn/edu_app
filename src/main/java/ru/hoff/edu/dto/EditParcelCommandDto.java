package ru.hoff.edu.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class EditParcelCommandDto implements BaseCommandDto {

    private final String id;
    private final String name;
    private final String form;
    private final String symbol;

    @Override
    public String getCommandType() {
        return "/edit";
    }
}
