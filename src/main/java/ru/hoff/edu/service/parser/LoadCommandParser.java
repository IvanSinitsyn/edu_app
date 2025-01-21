package ru.hoff.edu.service.parser;

import lombok.RequiredArgsConstructor;
import ru.hoff.edu.dto.BaseCommandDto;
import ru.hoff.edu.dto.LoadParcelsCommandDto;
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
public class LoadCommandParser implements CommandParser {

    private final static String PATTERN = "-parcels-(text|file) \"(.*?)\" -trucks \"(.*?)\" -algorithm \"(.*?)\" -out (\\w+)(?: -out-filename (\\w+))?";
    private final FileReaderFactory fileReaderFactory;

    @Override
    public BaseCommandDto parse(String command) {
        Pattern pattern = Pattern.compile(PATTERN);
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
        if (command.contains("-parcels-file")) {
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
}
