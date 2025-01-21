package ru.hoff.edu.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.hoff.edu.model.enums.AlgorithmType;
import ru.hoff.edu.model.enums.ResultOutType;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class LoadParcelsCommandDto implements BaseCommandDto {

    private final AlgorithmType algorithmType;
    private final List<String> parcelIds;
    private final List<String> trucksDescriptions;
    private final ResultOutType resultOutType;
    private final String pathToResultFile;

    @Override
    public String getCommandType() {
        return "/load";
    }
}
