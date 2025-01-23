package ru.hoff.edu.service.command.handler;

import lombok.Getter;
import ru.hoff.edu.dto.UnloadParcelsCommandDto;
import ru.hoff.edu.service.command.Command;

@Getter
public class UnloadParcelsCommandHandler implements Command<Void, UnloadParcelsCommandDto> {

    @Override
    public Void execute(UnloadParcelsCommandDto commandDto) {
//        try {
//            List<Map<String, Object>> truckDataList = inputFileReader.readFile(command.getInputFile());
//            fileWriter.writeToFile(command.getPathToResultFile(), truckDataList);
//        } catch (IOException ex) {
//            log.error("Error while processing file {}", command.getInputFile(), ex);
//        }
        return null;
    }
}
