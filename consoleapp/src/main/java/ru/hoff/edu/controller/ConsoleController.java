package ru.hoff.edu.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.stereotype.Controller;
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
import ru.hoff.edu.service.ParcelServiceClient;

@Controller
@ShellComponent
@RequiredArgsConstructor
public class ConsoleController {

    private final static String CREATE_COMMAND = "/create";
    private final static String EDIT_COMMAND = "/edit";
    private final static String FIND_COMMAND = "/find";
    private final static String FIND_ALL_COMMAND = "/findall";
    private final static String DELETE_COMMAND = "/delete";
    private final static String LOAD_COMMAND = "/load";
    private final static String UNLOAD_COMMAND = "/unload";
    private final ParcelServiceClient parcelServiceClient;

    /**
     * Создает новую посылку.
     * <p>
     * Эта команда создает новую посылку с указанными именем, формой и символом.
     * </p>
     *
     * @param name   Имя посылки.
     * @param form   Форма посылки.
     * @param symbol Символ, используемый для отображения посылки.
     */
    @ShellMethod(key = CREATE_COMMAND)
    public void createParcel(
            @ShellOption(value = {"--name", "-n"}) String name,
            @ShellOption(value = {"--form", "-f"}) String form,
            @ShellOption(value = {"--symbol", "-s"}) String symbol) {
        CreateParcelResponseDto response = parcelServiceClient.createParcel(new CreateParcelRequestDto(name, form, symbol));
        System.out.println(response);

    }

    /**
     * Редактирует существующую посылку.
     * <p>
     * Эта команда редактирует посылку с указанным идентификатором, обновляя её имя, форму и символ.
     * </p>
     *
     * @param id     Идентификатор посылки.
     * @param name   Новое имя посылки.
     * @param form   Новая форма посылки.
     * @param symbol Новый символ, используемый для отображения посылки.
     */
    @ShellMethod(key = EDIT_COMMAND)
    public void editParcel(
            @ShellOption(value = {"--id", "-i"}) String id,
            @ShellOption(value = {"--name", "-n"}) String name,
            @ShellOption(value = {"--form", "-f"}) String form,
            @ShellOption(value = {"--symbol", "-s"}) String symbol) {
        EditParcelResponseDto response = parcelServiceClient.editParcel(id, new EditParcelRequestDto(name, form, symbol));
        System.out.println(response);
    }

    /**
     * Находит посылку по имени.
     * <p>
     * Эта команда ищет посылку по указанному имени и возвращает информацию о ней.
     * </p>
     *
     * @param name Имя посылки для поиска.
     */
    @ShellMethod(key = FIND_COMMAND)
    public void findParcel(@ShellOption(value = {"--name", "-n"}) String name) {
        FindParcelByIdResponseDto response = parcelServiceClient.findParcelById(name);
        System.out.println(response);
    }

    /**
     * Находит все посылкы.
     * <p>
     * Эта команда ищет все посылки по указанным параметрам отступа и размера пула данных.
     * </p>
     *
     * @param page Размер отступа для выборки данных.
     * @param size Размер пула выборки данных.
     */
    @ShellMethod(key = FIND_ALL_COMMAND)
    public void findAllParcels(
            @ShellOption(value = {"--page", "-p"}) int page,
            @ShellOption(value = {"--size", "-s"}) int size) {
        FindAllParcelsResponseDto response = parcelServiceClient.findAllParcels(page, size);
        System.out.println(response);
    }

    /**
     * Удаляет посылку по имени.
     * <p>
     * Эта команда удаляет посылку с указанным именем.
     * </p>
     *
     * @param name Имя посылки для удаления.
     */
    @ShellMethod(key = DELETE_COMMAND)
    public void deleteParcel(@ShellOption(value = {"--name", "-n"}) String name) {
        DeleteParcelResponseDto response = parcelServiceClient.deleteParcel(name);
        System.out.println(response);
    }

    /**
     * Загружает посылки в грузовики.
     * <p>
     * Эта команда загружает посылки в грузовики в соответствии с указанным алгоритмом.
     * Посылки могут быть переданы в виде текста или считаны из файла.
     * </p>
     *
     * @param parcelsNames       Список имен посылок в виде текста (разделенных символом новой строки).
     * @param parcelsFile        Путь к файлу с именами посылок.
     * @param trucksDescriptions Описания грузовиков (разделенные символом новой строки).
     * @param algorithm          Алгоритм загрузки.
     * @param outputFormat       Формат вывода результата.
     * @param outputFilename     Имя файла для сохранения результата.
     */
    @ShellMethod(key = LOAD_COMMAND)
    public void loadParcel(
            @ShellOption(value = "--user-id") String userId,
            @ShellOption(value = "--parcels-text", defaultValue = "") String parcelsNames,
            @ShellOption(value = "--parcels-file", defaultValue = "") String parcelsFile,
            @ShellOption(value = "--trucks") String trucksDescriptions,
            @ShellOption(value = "--algorithm") String algorithm,
            @ShellOption(value = "--out") String outputFormat,
            @ShellOption(value = "--out-filename", defaultValue = "") String outputFilename) {
        LoadParcelsResponseDto response = parcelServiceClient.loadParcels(
                new LoadParcelRequestDto(
                        userId,
                        algorithm,
                        parcelsNames,
                        parcelsFile,
                        trucksDescriptions,
                        outputFormat,
                        outputFilename)
        );
        System.out.println(response);
    }

    /**
     * Выгружает посылки из файла.
     * <p>
     * Эта команда выгружает посылки из указанного файла и сохраняет результат в другой файл.
     * </p>
     *
     * @param infile    Путь к файлу с данными о посылках.
     * @param outfile   Путь к файлу для сохранения результата.
     * @param withCount Флаг, указывающий на необходимость подсчета количества посылок.
     */
    @ShellMethod(key = UNLOAD_COMMAND)
    public void unloadParcel(
            @ShellOption(value = "--infile") String infile,
            @ShellOption(value = "--outfile") String outfile,
            @ShellOption(value = "--withcount", defaultValue = "") String withCount
    ) {
        parcelServiceClient.unloadParcels(new UnloadParcelsRequestDto(infile, outfile, !"".equals(withCount)));
    }
}
