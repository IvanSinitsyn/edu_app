package ru.hoff.edu.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class CreateParcelCommandDto implements BaseCommandDto {
    private final String name;
    private final String form;
    private final String symbol;

    @Override
    public String getCommandType() {
        return "/create";
    }
}
