package ru.hoff.edu.service;

import org.mapstruct.Mapper;
import ru.hoff.edu.model.Cheque;
import ru.hoff.edu.model.entity.ChequeEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ChequeMapper {

    List<Cheque> toDtoList(List<ChequeEntity> chequeEntity);

    Cheque toDto(ChequeEntity chequeEntity);

    ChequeEntity toEntity(Cheque cheque);
}
