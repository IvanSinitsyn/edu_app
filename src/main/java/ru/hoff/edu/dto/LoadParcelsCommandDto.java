package ru.hoff.edu.dto;

import lombok.Getter;
import ru.hoff.edu.model.enums.AlgorithmType;
import ru.hoff.edu.model.enums.ResultOutType;

import java.util.List;

@Getter
public class LoadParcelsCommandDto implements BaseCommandDto {

    private final AlgorithmType algorithmType;
    private final List<String> parcelIds;
    private final List<String> trucksDescriptions;
    private final ResultOutType resultOutType;
    private final String pathToResultFile;

    public LoadParcelsCommandDto(AlgorithmType algorithmType, List<String> parcelIds, List<String> trucksDescriptions, ResultOutType resultOutType, String pathToResultFile) {
        this.algorithmType = algorithmType;
        this.parcelIds = parcelIds;
        this.trucksDescriptions = trucksDescriptions;
        this.resultOutType = resultOutType;
        this.pathToResultFile = pathToResultFile;
    }

    @Override
    public String getCommandType() {
        return "/load";
    }
}
