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
import ru.hoff.edu.dto.CreateParcelCommandDto;
import ru.hoff.edu.dto.DeleteParcelCommandDto;
import ru.hoff.edu.dto.EditParcelCommandDto;
import ru.hoff.edu.dto.FindAllParcelsQueryDto;
import ru.hoff.edu.dto.FindParcelByIdQueryDto;
import ru.hoff.edu.dto.LoadParcelsCommandDto;
import ru.hoff.edu.dto.UnloadParcelsCommandDto;
import ru.hoff.edu.dto.request.LoadParcelRequestDto;
import ru.hoff.edu.dto.response.DeleteParcelResponseDto;
import ru.hoff.edu.dto.response.EditParcelResponseDto;
import ru.hoff.edu.dto.response.FindAllParcelsResponseDto;
import ru.hoff.edu.dto.response.FindParcelByIdResponseDto;
import ru.hoff.edu.dto.response.LoadParcelsResponseDto;
import ru.hoff.edu.model.enums.FileType;
import ru.hoff.edu.service.factory.FileReaderFactory;
import ru.hoff.edu.service.filereader.InputFileReader;
import ru.hoff.edu.service.handler.impl.ConsoleCommandHandler;

import java.util.List;

import static ru.hoff.edu.util.FileExtensionParser.getFileExtension;

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

    private final ConsoleCommandHandler commandHandler;
    private final FileReaderFactory fileReaderFactory;

    /**
     * Создает новую посылку.
     * <p>
     * Эта команда создает новую посылку с указанными именем, формой и символом.
     * </p>
     *
     * @param createParcelCommandDto DTO для создания посылки.
     * @see CreateParcelCommandDto
     */
    @PostMapping()
    @Operation(summary = "Создание новой посылки")
    @ApiResponse(responseCode = "201", description = "Посылка создана")
    public ResponseEntity<?> createParcel(
            @Parameter(description = "Данные для создания посылки", required = true)
            @RequestBody CreateParcelCommandDto createParcelCommandDto) {
        commandHandler.handle(createParcelCommandDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * Редактирует существующую посылку.
     * <p>
     * Эта команда редактирует посылку с указанным идентификатором, обновляя её имя, форму и символ.
     * </p>
     *
     * @param editParcelCommandDto DTO для обновления посылки.
     * @see EditParcelCommandDto
     */
    @PutMapping()
    @Operation(summary = "Изменение существующей посылки")
    @ApiResponse(responseCode = "200", description = "Посылка изменена")
    public ResponseEntity<?> editParcel(
            @Parameter(description = "Данные для изменения посылки", required = true)
            @RequestBody EditParcelCommandDto editParcelCommandDto) {
        EditParcelResponseDto response = (EditParcelResponseDto) commandHandler.handle(editParcelCommandDto);
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
    public ResponseEntity<?> findParcelById(
            @Parameter(description = "Id для поиска посылки", required = true)
            @PathVariable(name = "id") String id) {
        FindParcelByIdResponseDto result = (FindParcelByIdResponseDto) commandHandler.handle(new FindParcelByIdQueryDto(id));
        if (result.getParcel() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**
     * Возвращает все посылки.
     * <p>
     * Эта команда возвращает все посылки.
     * </p>
     */
    @GetMapping("/all")
    @Operation(summary = "Получение всех посылок")
    @ApiResponse(responseCode = "200", description = "Посылки получены")
    public ResponseEntity<?> fetchAllParcels(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        FindAllParcelsResponseDto result = (FindAllParcelsResponseDto) commandHandler.handle(new FindAllParcelsQueryDto(page, size));
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
    public ResponseEntity<?> deleteParcelById(
            @Parameter(description = "Id посылки для удаления", required = true)
            @PathVariable(name = "id") String id) {
        DeleteParcelResponseDto result = (DeleteParcelResponseDto) commandHandler.handle(new DeleteParcelCommandDto(id));
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
        List<String> parcelIds;
        if (!loadParcelRequestDto.parcelIds().isEmpty()) {
            parcelIds = loadParcelRequestDto.parcelIds();
        } else {
            InputFileReader fileReader = fileReaderFactory.createFileReader(FileType.fromString(getFileExtension(loadParcelRequestDto.pathToParcelsFile())));
            parcelIds = fileReader.readFile(loadParcelRequestDto.pathToParcelsFile());
        }

        LoadParcelsResponseDto result = (LoadParcelsResponseDto) commandHandler.handle(new LoadParcelsCommandDto(
                loadParcelRequestDto.algorithmType(),
                parcelIds,
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
     * @param unloadParcelsCommandDto DTO для разгрузки посылок.
     * @see UnloadParcelsCommandDto
     */
    @PostMapping("/unload")
    @Operation(summary = "Выгружает посылки из файла.", description = "Выгружает посылки из указанного файла и сохраняет результат в другой файл.")
    @ApiResponse(responseCode = "200", description = "Посылки выгружены")
    public ResponseEntity<?> unloadParcels(
            @Parameter(description = "DTO для разгрузки посылок.", required = true)
            @RequestBody UnloadParcelsCommandDto unloadParcelsCommandDto) {
        commandHandler.handle(unloadParcelsCommandDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
