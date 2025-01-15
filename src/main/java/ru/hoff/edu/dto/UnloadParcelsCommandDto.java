package ru.hoff.edu.dto;

import lombok.Getter;

@Getter
public class UnloadParcelsCommandDto implements BaseCommandDto {

    private final String inFileName;
    private final String outFileName;
    private final boolean withCount;

    public UnloadParcelsCommandDto(String inFileName, String outFileName, boolean withCount) {
        this.inFileName = inFileName;
        this.outFileName = outFileName;
        this.withCount = withCount;
    }

    @Override
    public String getCommandType() {
        return "/unload";
    }
}
