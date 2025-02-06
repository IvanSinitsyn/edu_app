package ru.hoff.edu.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.hoff.edu.model.dto.request.EditParcelRequestDto;
import ru.hoff.edu.model.dto.request.LoadParcelRequestDto;
import ru.hoff.edu.model.dto.response.DeleteParcelResponseDto;
import ru.hoff.edu.model.dto.response.EditParcelResponseDto;
import ru.hoff.edu.model.dto.response.FindAllParcelsResponseDto;
import ru.hoff.edu.model.dto.response.FindParcelByIdResponseDto;
import ru.hoff.edu.model.dto.response.LoadParcelsResponseDto;
import ru.hoff.edu.service.Mediator;
import ru.hoff.edu.service.request.impl.CreateParcelRequest;
import ru.hoff.edu.service.request.impl.DeleteParcelRequest;
import ru.hoff.edu.service.request.impl.EditParcelRequest;
import ru.hoff.edu.service.request.impl.FindAllParcelsRequest;
import ru.hoff.edu.service.request.impl.FindParcelByIdRequest;
import ru.hoff.edu.service.request.impl.LoadParcelsRequest;
import ru.hoff.edu.service.request.impl.UnloadParcelsRequest;

/**
 * Контроллер для обработки команд, связанных с посылками, через Spring Shell.
 * <p>
 * Этот класс предоставляет команды для создания, редактирования, поиска, удаления,
 * загрузки и выгрузки посылок. Каждая команда обрабатывается соответствующим обработчиком.
 * </p>
 */
@RestController
@RequestMapping("api/v1/parcels")
@Tag(name = "Parcel API", description = "API для управления посылками")
@RequiredArgsConstructor
public class ParcelController {

    private final Mediator mediator;


    /**
     * Создает новую посылку.
     * <p>
     * Эта команда создает новую посылку с указанными именем, формой и символом.
     * </p>
     *
     * @param createParcelRequest DTO для создания посылки.
     * @see CreateParcelRequest
     */
    @PostMapping()
    @Operation(summary = "Создание новой посылки")
    @ApiResponse(responseCode = "201", description = "Посылка создана")
    public ResponseEntity<?> createParcel(
            @Parameter(description = "Данные для создания посылки", required = true)
            @RequestBody CreateParcelRequest createParcelRequest) {
        mediator.send(createParcelRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * Редактирует существующую посылку.
     * <p>
     * Эта команда редактирует посылку с указанным идентификатором, обновляя её имя, форму и символ.
     * </p>
     *
     * @param editParcelRequestDto DTO для обновления посылки.
     * @see EditParcelRequestDto
     */
    @PutMapping("/{id}")
    @Operation(summary = "Изменение существующей посылки")
    @ApiResponse(responseCode = "200", description = "Посылка изменена")
    public ResponseEntity<EditParcelResponseDto> editParcel(
            @Parameter(description = "Id изменяемой посылки", required = true)
            @PathVariable(name = "id") String id,
            @Parameter(description = "Данные для изменения посылки", required = true)
            @RequestBody EditParcelRequestDto editParcelRequestDto) {
        EditParcelResponseDto response = (EditParcelResponseDto) mediator.send(
                new EditParcelRequest(id, editParcelRequestDto.name(), editParcelRequestDto.form(), editParcelRequestDto.symbol()));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * Находит посылку по имени.
     * <p>
     * Эта команда ищет посылку по указанному имени и возвращает информацию о ней.
     * </p>
     *
     * @param id Имя посылки для поиска.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Поиск посылки по Id(имени)")
    @ApiResponse(responseCode = "200", description = "Посылка найдена")
    @ApiResponse(responseCode = "404", description = "Посылка не найдена")
    public ResponseEntity<FindParcelByIdResponseDto> findParcelById(
            @Parameter(description = "Id для поиска посылки", required = true)
            @PathVariable(name = "id") String id) {
        FindParcelByIdResponseDto response = (FindParcelByIdResponseDto) mediator.send(new FindParcelByIdRequest(id));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * Возвращает все посылки.
     * <p>
     * Эта команда возвращает все посылки.
     * </p>
     */
    @GetMapping("")
    @Operation(summary = "Получение всех посылок")
    @ApiResponse(responseCode = "200", description = "Посылки получены")
    public ResponseEntity<FindAllParcelsResponseDto> fetchAllParcels(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        FindAllParcelsResponseDto result = (FindAllParcelsResponseDto) mediator.send(new FindAllParcelsRequest(page, size));
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }


    /**
     * Удаляет посылку по имени.
     * <p>
     * Эта команда удаляет посылку с указанным именем.
     * </p>
     *
     * @param id Имя посылки для удаления.
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление посылки по Id(имени)")
    @ApiResponse(responseCode = "200", description = "Посылка удалена")
    public ResponseEntity<DeleteParcelResponseDto> delete(
            @Parameter(description = "Id посылки для удаления", required = true)
            @PathVariable(name = "id") String id) {
        DeleteParcelResponseDto result = (DeleteParcelResponseDto) mediator.send(new DeleteParcelRequest(id));
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**
     * Загружает посылки в грузовики.
     * <p>
     * Эта команда загружает посылки в грузовики в соответствии с указанным алгоритмом.
     * Посылки могут быть переданы в виде текста или считаны из файла.
     * </p>
     *
     * @param loadParcelRequestDto DTO для загрузки посылок.
     * @see LoadParcelRequestDto
     */
    @PostMapping("/load")
    @Operation(
            summary = "Загружает посылки в грузовики",
            description = "Эта команда загружает посылки в грузовики в соответствии с указанным алгоритмом. Посылки могут быть переданы в виде текста или считаны из файла.")
    @ApiResponse(responseCode = "200", description = "Посылки погружены")
    public ResponseEntity<?> loadParcels(
            @Parameter(description = "DTO для загрузки посылок", required = true)
            @RequestBody LoadParcelRequestDto loadParcelRequestDto) {
        LoadParcelsResponseDto result = (LoadParcelsResponseDto) mediator.send(new LoadParcelsRequest(
                loadParcelRequestDto.algorithmType(),
                loadParcelRequestDto.parcelIds(),
                loadParcelRequestDto.pathToParcelsFile(),
                loadParcelRequestDto.trucksDescriptions(),
                loadParcelRequestDto.resultOutType(),
                loadParcelRequestDto.pathToResultFile()));
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }


    /**
     * Выгружает посылки из файла.
     * <p>
     * Эта команда выгружает посылки из указанного файла и сохраняет результат в другой файл.
     * </p>
     *
     * @param unloadParcelsRequest DTO для разгрузки посылок.
     * @see UnloadParcelsRequest
     */
    @PostMapping("/unload")
    @Operation(summary = "Выгружает посылки из файла.", description = "Выгружает посылки из указанного файла и сохраняет результат в другой файл.")
    @ApiResponse(responseCode = "200", description = "Посылки выгружены")
    public ResponseEntity<?> unloadParcels(
            @Parameter(description = "DTO для разгрузки посылок.", required = true)
            @RequestBody UnloadParcelsRequest unloadParcelsRequest) {
        mediator.send(unloadParcelsRequest);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
