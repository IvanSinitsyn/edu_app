package ru.hoff.edu.dto;

import lombok.Getter;

@Getter
public class CreateParcelCommandDto implements BaseCommandDto {
    private final String name;
    private final String form;
    private final String symbol;

    public CreateParcelCommandDto(String name, String form, String symbol) {
        this.name = name;
        this.form = form;
        this.symbol = symbol;
    }

    @Override
    public String getCommandType() {
        return "/create";
    }
}
