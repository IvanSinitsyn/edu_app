package ru.hoff.edu.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.hoff.edu.model.dto.request.CreateParcelRequestDto;
import ru.hoff.edu.model.dto.request.DeleteParcelRequestDto;
import ru.hoff.edu.model.dto.request.EditParcelRequestDto;
import ru.hoff.edu.model.dto.request.FindAllParcelsRequestDto;
import ru.hoff.edu.model.dto.request.FindParcelByIdRequestDto;
import ru.hoff.edu.model.dto.request.LoadParcelRequestDto;
import ru.hoff.edu.model.dto.request.UnloadParcelsRequestDto;
import ru.hoff.edu.model.dto.response.CreateParcelResponseDto;
import ru.hoff.edu.model.dto.response.DeleteParcelResponseDto;
import ru.hoff.edu.model.dto.response.EditParcelResponseDto;
import ru.hoff.edu.model.dto.response.FindAllParcelsResponseDto;
import ru.hoff.edu.model.dto.response.FindParcelByIdResponseDto;
import ru.hoff.edu.model.dto.response.LoadParcelsResponseDto;

@Service
@RequiredArgsConstructor
public class ParcelServiceCaller {

    private final ParcelServiceClient parcelServiceClient;

    public Object call(Request request) {
        if (request instanceof CreateParcelRequestDto) {
            return createParcel(request);
        }

        if (request instanceof DeleteParcelRequestDto) {
            return deleteParcel(request);
        }

        if (request instanceof EditParcelRequestDto) {
            return editParcel(request);
        }

        if (request instanceof FindAllParcelsRequestDto) {
            return findAllParcels(request);
        }

        if (request instanceof FindParcelByIdRequestDto) {
            return findParcelById(request);
        }

        if (request instanceof LoadParcelRequestDto) {
            return loadParcels(request);
        }

        if (request instanceof UnloadParcelsRequestDto) {
            return unloadParcels(request);
        }

        return null;
    }

    private CreateParcelResponseDto createParcel(Request request) {
        return parcelServiceClient.createParcel((CreateParcelRequestDto) request);
    }

    private DeleteParcelResponseDto deleteParcel(Request request) {
        DeleteParcelRequestDto deleteRequest = (DeleteParcelRequestDto) request;
        return parcelServiceClient.deleteParcel(deleteRequest.parcelName());
    }

    private EditParcelResponseDto editParcel(Request request) {
        EditParcelRequestDto editRequest = (EditParcelRequestDto) request;
        return parcelServiceClient.editParcel(editRequest.id(), editRequest);
    }

    private FindAllParcelsResponseDto findAllParcels(Request request) {
        FindAllParcelsRequestDto findAllRequest = (FindAllParcelsRequestDto) request;
        return parcelServiceClient.findAllParcels(findAllRequest.page(), findAllRequest.size());
    }

    private FindParcelByIdResponseDto findParcelById(Request request) {
        FindParcelByIdRequestDto findRequest = (FindParcelByIdRequestDto) request;
        return parcelServiceClient.findParcelById(findRequest.parcelName());
    }

    private LoadParcelsResponseDto loadParcels(Request request) {
        LoadParcelRequestDto loadRequest = (LoadParcelRequestDto) request;
        return parcelServiceClient.loadParcels(loadRequest);
    }

    private ResponseEntity<?> unloadParcels(Request request) {
        UnloadParcelsRequestDto unloadRequest = (UnloadParcelsRequestDto) request;
        return parcelServiceClient.unloadParcels(unloadRequest);
    }
}
