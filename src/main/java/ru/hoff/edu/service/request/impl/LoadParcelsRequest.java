package ru.hoff.edu.service.request.impl;

import lombok.Builder;
import ru.hoff.edu.model.enums.AlgorithmType;
import ru.hoff.edu.model.enums.ResultOutType;
import ru.hoff.edu.service.request.Request;

import java.util.List;

@Builder
public record LoadParcelsRequest(String userId,
                                 AlgorithmType algorithmType,
                                 List<String> parcelIds,
                                 String pathToParcelsFile,
                                 List<String> trucksDescriptions,
                                 ResultOutType resultOutType,
                                 String pathToResultFile) implements Request {
}
