package ru.hoff.edu.service;

import ru.hoff.edu.model.enums.ResultOutType;

public class ReportWriterFactory {

    public ReportWriter createReportWriter(ResultOutType resultOutType, String outputPath) {
        return switch (resultOutType) {
            case TEXT -> new TextReportWriter();
            case FILE -> new JsonFileReportWriter(outputPath);
        };
    }
}
