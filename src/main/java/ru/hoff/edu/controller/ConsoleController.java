package ru.hoff.edu.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.hoff.edu.model.dto.response.CreateParcelResponseDto;
import ru.hoff.edu.model.dto.response.DeleteParcelResponseDto;
import ru.hoff.edu.model.dto.response.EditParcelResponseDto;
import ru.hoff.edu.model.dto.response.FindParcelByIdResponseDto;
import ru.hoff.edu.model.dto.response.LoadParcelsResponseDto;
import ru.hoff.edu.model.enums.AlgorithmType;
import ru.hoff.edu.model.enums.ResultOutType;
import ru.hoff.edu.service.Mediator;
import ru.hoff.edu.service.request.impl.CreateParcelRequest;
import ru.hoff.edu.service.request.impl.DeleteParcelRequest;
import ru.hoff.edu.service.request.impl.EditParcelRequest;
import ru.hoff.edu.service.request.impl.FindParcelByIdRequest;
import ru.hoff.edu.service.request.impl.LoadParcelsRequest;
import ru.hoff.edu.service.request.impl.UnloadParcelsRequest;

import java.util.Arrays;

@ShellComponent
@RequiredArgsConstructor
public class ConsoleController {

    private final static String CREATE_COMMAND = "/create";
    private final static String EDIT_COMMAND = "/edit";
    private final static String FIND_COMMAND = "/find";
    private final static String DELETE_COMMAND = "/delete";
    private final static String LOAD_COMMAND = "/load";
    private final static String UNLOAD_COMMAND = "/unload";
    private final Mediator mediator;

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
        CreateParcelResponseDto response = (CreateParcelResponseDto) mediator.send(new CreateParcelRequest(name, form, symbol));
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
        EditParcelResponseDto response = (EditParcelResponseDto) mediator.send(new EditParcelRequest(id, name, form, symbol));
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
        FindParcelByIdResponseDto response = (FindParcelByIdResponseDto) mediator.send(new FindParcelByIdRequest(name));
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
        DeleteParcelResponseDto response = (DeleteParcelResponseDto) mediator.send(new DeleteParcelRequest(name));
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
            @ShellOption(value = "--parcels-text", defaultValue = "") String parcelsNames,
            @ShellOption(value = "--parcels-file", defaultValue = "") String parcelsFile,
            @ShellOption(value = "--trucks") String trucksDescriptions,
            @ShellOption(value = "--algorithm") String algorithm,
            @ShellOption(value = "--out") String outputFormat,
            @ShellOption(value = "--out-filename", defaultValue = "") String outputFilename) {
        LoadParcelsResponseDto response = (LoadParcelsResponseDto) mediator.send(
                new LoadParcelsRequest(
                        AlgorithmType.fromString(algorithm),
                        Arrays.stream(parcelsNames.split(",")).toList(),
                        parcelsFile,
                        Arrays.stream(trucksDescriptions.split("\\n")).toList(),
                        ResultOutType.fromString(outputFormat),
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
        mediator.send(new UnloadParcelsRequest(infile, outfile, !"".equals(withCount)));
    }
}
