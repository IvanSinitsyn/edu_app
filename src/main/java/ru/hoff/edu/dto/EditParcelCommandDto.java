package ru.hoff.edu.dto;

import lombok.Getter;

@Getter
public class EditParcelCommandDto implements BaseCommandDto {

    private final String id;
    private final String name;
    private final String form;
    private final String symbol;

    public EditParcelCommandDto(String id, String name, String form, String symbol) {
        this.id = id;
        this.name = name;
        this.form = form;
        this.symbol = symbol;
    }

    @Override
    public String getCommandType() {
        return "/edit";
    }
}
