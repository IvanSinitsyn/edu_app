package ru.hoff.edu.service.factory;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import ru.hoff.edu.model.enums.ResultOutType;
import ru.hoff.edu.service.JsonFileReportWriter;
import ru.hoff.edu.service.ReportWriter;
import ru.hoff.edu.service.TextReportWriter;

/**
 * Фабрика для создания писателей отчетов.
 * В зависимости от типа вывода результата создает соответствующий писатель отчетов.
 */
@Component
public class ReportWriterFactory {

    /**
     * Создает писатель отчетов на основе переданного типа вывода результата.
     *
     * @param resultOutType Тип вывода результата (например, текстовый вывод или вывод в файл).
     * @param outputPath    Путь к файлу, если выбран тип вывода в файл.
     * @return Писатель отчетов, соответствующий переданному типу вывода.
     */
    public ReportWriter createReportWriter(ResultOutType resultOutType, String outputPath) {
        return switch (resultOutType) {
            case TEXT -> new TextReportWriter();
            case FILE -> new JsonFileReportWriter(outputPath, new ObjectMapper().writerWithDefaultPrettyPrinter());
        };
    }
}
