package ru.hoff.edu.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.hoff.edu.model.dto.request.EditParcelRequestDto;
import ru.hoff.edu.model.dto.request.LoadParcelRequestDto;
import ru.hoff.edu.model.dto.response.ResponseDto;
import ru.hoff.edu.service.mapper.LoadParcelRequestMapper;
import ru.hoff.edu.service.mediator.Mediator;
import ru.hoff.edu.service.mediator.request.impl.CreateParcelRequest;
import ru.hoff.edu.service.mediator.request.impl.DeleteParcelRequest;
import ru.hoff.edu.service.mediator.request.impl.EditParcelRequest;
import ru.hoff.edu.service.mediator.request.impl.FindAllParcelsRequest;
import ru.hoff.edu.service.mediator.request.impl.FindParcelByIdRequest;
import ru.hoff.edu.service.mediator.request.impl.UnloadParcelsRequestDto;

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
public class ParcelController {

    private final Mediator mediator;
    private final LoadParcelRequestMapper mapper;

    @Autowired
    public ParcelController(Mediator mediator,
                            @Qualifier("loadParcelRequestMapperImpl") LoadParcelRequestMapper mapper) {
        this.mediator = mediator;
        this.mapper = mapper;
    }

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
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDto createParcel(
            @Parameter(description = "Данные для создания посылки", required = true)
            @RequestBody CreateParcelRequest createParcelRequest) {
        return mediator.send(createParcelRequest);
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
    @ResponseStatus(HttpStatus.OK)
    public ResponseDto editParcel(
            @Parameter(description = "Id изменяемой посылки", required = true)
            @PathVariable(name = "id") String id,
            @Parameter(description = "Данные для изменения посылки", required = true)
            @RequestBody EditParcelRequestDto editParcelRequestDto) {
        return mediator.send(
                new EditParcelRequest(
                        id,
                        editParcelRequestDto.name(),
                        editParcelRequestDto.form(),
                        editParcelRequestDto.symbol()));
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
    @ResponseStatus(HttpStatus.OK)
    public ResponseDto findParcelById(
            @Parameter(description = "Id для поиска посылки", required = true)
            @PathVariable(name = "id") String id) {
        return mediator.send(new FindParcelByIdRequest(id));
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
    @ResponseStatus(HttpStatus.OK)
    public ResponseDto fetchAllParcels(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        return mediator.send(new FindAllParcelsRequest(page, size));
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
    @ResponseStatus(HttpStatus.OK)
    public ResponseDto delete(
            @Parameter(description = "Id посылки для удаления", required = true)
            @PathVariable(name = "id") String id) {
        return mediator.send(new DeleteParcelRequest(id));
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
    @ResponseStatus(HttpStatus.OK)
    public ResponseDto loadParcels(
            @Parameter(description = "DTO для загрузки посылок", required = true)
            @RequestBody LoadParcelRequestDto loadParcelRequestDto) {
        return mediator.send(mapper.toRequest(loadParcelRequestDto));
    }


    /**
     * Выгружает посылки из файла.
     * <p>
     * Эта команда выгружает посылки из указанного файла и сохраняет результат в другой файл.
     * </p>
     *
     * @param unloadParcelsRequestDto DTO для разгрузки посылок.
     * @see UnloadParcelsRequestDto
     */
    @PostMapping("/unload")
    @Operation(summary = "Выгружает посылки из файла.", description = "Выгружает посылки из указанного файла и сохраняет результат в другой файл.")
    @ApiResponse(responseCode = "200", description = "Посылки выгружены")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDto unloadParcels(
            @Parameter(description = "DTO для разгрузки посылок.", required = true)
            @RequestBody UnloadParcelsRequestDto unloadParcelsRequestDto) {
        return mediator.send(unloadParcelsRequestDto);
    }
}
