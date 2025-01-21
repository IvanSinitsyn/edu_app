package ru.hoff.edu.service.factory;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import ru.hoff.edu.model.enums.ResultOutType;
import ru.hoff.edu.service.JsonFileReportWriter;
import ru.hoff.edu.service.ReportWriter;
import ru.hoff.edu.service.TextReportWriter;

@Component
public class ReportWriterFactory {

    public ReportWriter createReportWriter(ResultOutType resultOutType, String outputPath) {
        return switch (resultOutType) {
            case TEXT -> new TextReportWriter();
            case FILE -> new JsonFileReportWriter(outputPath, new ObjectMapper().writerWithDefaultPrettyPrinter());
        };
    }
}
