package ru.hoff.edu.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UnloadParcelsCommandDto implements BaseCommandDto {

    private final String inFileName;
    private final String outFileName;
    private final boolean withCount;

    @Override
    public String getCommandType() {
        return "/unload";
    }
}
