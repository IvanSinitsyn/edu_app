package ru.hoff.edu.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.hoff.edu.dto.CreateParcelCommandDto;
import ru.hoff.edu.dto.DeleteParcelCommandDto;
import ru.hoff.edu.dto.EditParcelCommandDto;
import ru.hoff.edu.dto.FindParcelByIdQueryDto;
import ru.hoff.edu.dto.LoadParcelsCommandDto;
import ru.hoff.edu.dto.UnloadParcelsCommandDto;
import ru.hoff.edu.model.enums.AlgorithmType;
import ru.hoff.edu.model.enums.FileType;
import ru.hoff.edu.model.enums.ResultOutType;
import ru.hoff.edu.service.factory.FileReaderFactory;
import ru.hoff.edu.service.filereader.InputFileReader;
import ru.hoff.edu.service.handler.impl.ConsoleCommandHandler;

import java.util.Arrays;
import java.util.List;

import static ru.hoff.edu.util.FileExtensionParser.getFileExtension;

/**
 * Контроллер для обработки команд, связанных с посылками, через Spring Shell.
 * <p>
 * Этот класс предоставляет команды для создания, редактирования, поиска, удаления,
 * загрузки и выгрузки посылок. Каждая команда обрабатывается соответствующим обработчиком.
 * </p>
 */
@ShellComponent
@RequiredArgsConstructor
public class ParcelController {

    private final static String CREATE_COMMAND = "/create";
    private final static String EDIT_COMMAND = "/edit";
    private final static String FIND_COMMAND = "/find";
    private final static String DELETE_COMMAND = "/delete";
    private final static String LOAD_COMMAND = "/load";
    private final static String UNLOAD_COMMAND = "/unload";
    private final ConsoleCommandHandler commandHandler;
    private final FileReaderFactory fileReaderFactory;

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
        commandHandler.handle(new CreateParcelCommandDto(name, form, symbol));
    }

    /**
     * Редактирует существующую посылку.
     * <p>
     * Эта команда редактирует посылку с указанным идентификатором, обновляя её имя, форму и символ.
     * </p>
     *
     * @param id Идентификатор посылки.
     * @param name Новое имя посылки.
     * @param form Новая форма посылки.
     * @param symbol Новый символ, используемый для отображения посылки.
     */
    @ShellMethod(key = EDIT_COMMAND)
    public void editParcel(
            @ShellOption(value = {"--id", "-i"}) String id,
            @ShellOption(value = {"--name", "-n"}) String name,
            @ShellOption(value = {"--form", "-f"}) String form,
            @ShellOption(value = {"--symbol", "-s"}) String symbol) {
        commandHandler.handle(new EditParcelCommandDto(id, name, form, symbol));
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
        commandHandler.handle(new FindParcelByIdQueryDto(name));
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
        commandHandler.handle(new DeleteParcelCommandDto(name));
    }

    /**
     * Загружает посылки в грузовики.
     * <p>
     * Эта команда загружает посылки в грузовики в соответствии с указанным алгоритмом.
     * Посылки могут быть переданы в виде текста или считаны из файла.
     * </p>
     *
     * @param parcelsNames Список имен посылок в виде текста (разделенных символом новой строки).
     * @param parcelsFile Путь к файлу с именами посылок.
     * @param trucksDescriptions Описания грузовиков (разделенные символом новой строки).
     * @param algorithm Алгоритм загрузки.
     * @param outputFormat Формат вывода результата.
     * @param outputFilename Имя файла для сохранения результата.
     */
    @ShellMethod(key = LOAD_COMMAND)
    public void loadParcel(
            @ShellOption(value = "--parcels-text", defaultValue = "") String parcelsNames,
            @ShellOption(value = "--parcels-file", defaultValue = "") String parcelsFile,
            @ShellOption(value = "--trucks") String trucksDescriptions,
            @ShellOption(value = "--algorithm") String algorithm,
            @ShellOption(value = "--out") String outputFormat,
            @ShellOption(value = "--out-filename", defaultValue = "") String outputFilename) {
        List<String> parcelIds;
        if (!parcelsNames.isEmpty()) {
            String normalizedParcelsText = parcelsNames.replace("\\n", "\n");
            parcelIds = Arrays.asList(normalizedParcelsText.split("\n"));
        } else {
            InputFileReader fileReader = fileReaderFactory.createFileReader(FileType.fromString(getFileExtension(parcelsFile)));
            parcelIds = fileReader.readFile(parcelsFile);
        }
        String normalizedTrucksDescriptions = trucksDescriptions.replace("\\n", "\n");

        commandHandler.handle(new LoadParcelsCommandDto(
                AlgorithmType.fromString(algorithm),
                parcelIds,
                Arrays.asList(normalizedTrucksDescriptions.split("\n")),
                ResultOutType.fromString(outputFormat),
                outputFilename)
        );
    }

    /**
     * Выгружает посылки из файла.
     * <p>
     * Эта команда выгружает посылки из указанного файла и сохраняет результат в другой файл.
     * </p>
     *
     * @param infile Путь к файлу с данными о посылках.
     * @param outfile Путь к файлу для сохранения результата.
     * @param withCount Флаг, указывающий на необходимость подсчета количества посылок.
     */
    @ShellMethod(key = UNLOAD_COMMAND)
    public void unloadParcel(
            @ShellOption(value = "--infile") String infile,
            @ShellOption(value = "--outfile") String outfile,
            @ShellOption(value = "--withcount", defaultValue = "") String withCount
    ) {
        commandHandler.handle(new UnloadParcelsCommandDto(infile, outfile, !withCount.isEmpty()));
    }
}
