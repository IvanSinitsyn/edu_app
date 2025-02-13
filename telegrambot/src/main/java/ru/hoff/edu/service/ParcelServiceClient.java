package ru.hoff.edu.service;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.DeleteExchange;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;
import ru.hoff.edu.model.dto.request.CreateParcelRequestDto;
import ru.hoff.edu.model.dto.request.EditParcelRequestDto;
import ru.hoff.edu.model.dto.request.LoadParcelRequestDto;
import ru.hoff.edu.model.dto.request.UnloadParcelsRequestDto;
import ru.hoff.edu.model.dto.response.CreateParcelResponseDto;
import ru.hoff.edu.model.dto.response.DeleteParcelResponseDto;
import ru.hoff.edu.model.dto.response.EditParcelResponseDto;
import ru.hoff.edu.model.dto.response.FindAllParcelsResponseDto;
import ru.hoff.edu.model.dto.response.FindParcelByIdResponseDto;
import ru.hoff.edu.model.dto.response.LoadParcelsResponseDto;
import ru.hoff.edu.model.dto.response.UnloadParcelsResponseDto;

public interface ParcelServiceClient {

    @PostExchange("/api/v1/parcels")
    CreateParcelResponseDto createParcel(@RequestBody CreateParcelRequestDto createParcelRequest);

    @PutMapping("/api/v1/parcels/{id}")
    EditParcelResponseDto editParcel(@PathVariable String id, @RequestBody EditParcelRequestDto editParcelRequestDto);

    @GetExchange("/api/v1/parcels/{id}")
    FindParcelByIdResponseDto findParcelById(@PathVariable String id);

    @GetExchange("/api/v1/parcels")
    FindAllParcelsResponseDto findAllParcels(@RequestParam int page, @RequestParam int size);

    @DeleteExchange("/api/v1/parcels/{id}")
    DeleteParcelResponseDto deleteParcel(@PathVariable String id);

    @PostExchange("/api/v1/parcels/load")
    LoadParcelsResponseDto loadParcels(@RequestBody LoadParcelRequestDto loadParcelRequestDto);

    @PostExchange("/api/v1/parcels/unload")
    UnloadParcelsResponseDto unloadParcels(@RequestBody UnloadParcelsRequestDto unloadParcelsRequest);
}
