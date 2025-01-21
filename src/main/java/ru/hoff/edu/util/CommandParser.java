package ru.hoff.edu.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.hoff.edu.dto.BaseCommandDto;
import ru.hoff.edu.dto.CreateParcelCommandDto;
import ru.hoff.edu.dto.DeleteParcelCommandDto;
import ru.hoff.edu.dto.EditParcelCommandDto;
import ru.hoff.edu.dto.FindParcelByIdQueryDto;
import ru.hoff.edu.dto.LoadParcelsCommandDto;
import ru.hoff.edu.dto.UnloadParcelsCommandDto;
import ru.hoff.edu.model.enums.AlgorithmType;
import ru.hoff.edu.model.enums.FileType;
import ru.hoff.edu.model.enums.ResultOutType;
import ru.hoff.edu.util.filereader.FileReaderFactory;
import ru.hoff.edu.util.filereader.InputFileReader;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static ru.hoff.edu.util.FileExtensionParser.getFileExtension;

@RequiredArgsConstructor
@Component
public class CommandParser {

    private final FileReaderFactory fileReaderFactory;

    public BaseCommandDto parseCommand(String commandData) {
        if (commandData.startsWith("/create")) {
            return parseCreateParcelCommand(commandData);
        } else if (commandData.startsWith("/delete")) {
            return parseDeleteParcelCommand(commandData);
        } else if (commandData.startsWith("/edit")) {
            return parseEditParcelCommand(commandData);
        } else if (commandData.startsWith("/find")) {
            return parseFindParcelByIdQuery(commandData);
        } else if (commandData.startsWith("/load")) {
            return parseLoadCommand(commandData);
        } else if (commandData.startsWith("/unload")) {
            return parseUnloadCommand(commandData);
        } else {
            throw new IllegalArgumentException("Неизвестная команда");
        }
    }

    private LoadParcelsCommandDto parseLoadCommand(String command) {
        Pattern pattern = Pattern.compile(
                "--arcels-(text|file) \"(.*?)\" >trucks \"(.*?)\" >algorithm \"(.*?)\" >out (\\w+)(?: >out-filename (\\w+))?");
        Matcher matcher = pattern.matcher(command);

        if (!matcher.find()) {
            throw new IllegalArgumentException("Ошибка в синтаксисе команды load");
        }

        String parcelsText = matcher.group(2);
        String trucksDescriptions = matcher.group(3);
        String algorithm = matcher.group(4);
        String outputFormat = matcher.group(5);
        String outputFilename = matcher.group(6);

        String normalizedParcelsText = parcelsText.replace("\\n", "\n");
        String normalizedTrucksDescriptions = trucksDescriptions.replace("\\n", "\n");

        List<String> parcelsNames = Arrays.asList(normalizedParcelsText.split("\n"));
        if (command.contains(">parcels-file")) {
            InputFileReader fileReader = fileReaderFactory.createFileReader(FileType.fromString(getFileExtension(outputFilename)));
            parcelsNames = fileReader.readFile(parcelsText);
        }

        return new LoadParcelsCommandDto(
                AlgorithmType.fromString(algorithm),
                parcelsNames,
                Arrays.asList(normalizedTrucksDescriptions.split("\n")),
                ResultOutType.fromString(outputFormat),
                outputFilename);
    }

    private UnloadParcelsCommandDto parseUnloadCommand(String command) {
        Pattern pattern = Pattern.compile(
                ">infile \"(.*?)\" >outfile \"(.*?)\"(?: >withcount)?"
        );

        Matcher matcher = pattern.matcher(command);

        if (!matcher.find()) {
            throw new IllegalArgumentException("Ошибка в синтаксисе команды unload");
        }

        String inFileName = matcher.group(1);
        String outFileName = matcher.group(2);
        boolean withCount = command.contains(">withcount");

        return new UnloadParcelsCommandDto(inFileName, outFileName, withCount);
    }

    private CreateParcelCommandDto parseCreateParcelCommand(String command) {
        Pattern pattern = Pattern.compile("-name (.*?) -form (.*?) -symbol (.*?)");
        Matcher matcher = pattern.matcher(command);

        if (!matcher.find()) {
            throw new IllegalArgumentException("Ошибка в синтаксисе команды /create");
        }

        String name = matcher.group(1);
        String form = matcher.group(2);
        String symbol = matcher.group(3);

        String normalizedForm = form.replace("\\n", "\n");
        return new CreateParcelCommandDto(name, normalizedForm, symbol);
    }

    private DeleteParcelCommandDto parseDeleteParcelCommand(String command) {
        Pattern pattern = Pattern.compile("\"(.*?)\"");
        Matcher matcher = pattern.matcher(command);

        if (!matcher.find()) {
            throw new IllegalArgumentException("Ошибка в синтаксисе команды /delete");
        }

        String parcelName = matcher.group(1);
        return new DeleteParcelCommandDto(parcelName);
    }

    private EditParcelCommandDto parseEditParcelCommand(String command) {
        Pattern pattern = Pattern.compile("--id \"(.*?)\" --name \"(.*?)\" --form \"(.*?)\" --symbol \"(.*?)\"");
        Matcher matcher = pattern.matcher(command);

        if (!matcher.find()) {
            throw new IllegalArgumentException("Ошибка в синтаксисе команды /edit");
        }

        String id = matcher.group(1);
        String name = matcher.group(2);
        String form = matcher.group(3);
        String symbol = matcher.group(4);

        String normalizedForm = form.replace("\\n", "\n");
        return new EditParcelCommandDto(id, name, normalizedForm, symbol);
    }

    private FindParcelByIdQueryDto parseFindParcelByIdQuery(String command) {
        Pattern pattern = Pattern.compile("\"(.*?)\"");
        Matcher matcher = pattern.matcher(command);

        if (!matcher.find()) {
            throw new IllegalArgumentException("Ошибка в синтаксисе команды /find");
        }

        String parcelName = matcher.group(1);
        return new FindParcelByIdQueryDto(parcelName);
    }
}
