package ru.hoff.edu.service.factory;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.hoff.edu.model.enums.ResultOutType;
import ru.hoff.edu.service.ReportWriter;

import java.util.Map;

/**
 * Фабрика для создания писателей отчетов.
 * В зависимости от типа вывода результата создает соответствующий писатель отчетов.
 */
@Component
@RequiredArgsConstructor
public class ReportWriterFactory {

    private final Map<ResultOutType, ReportWriter> reportWriters;

    /**
     * Создает писатель отчетов на основе переданного типа вывода результата.
     *
     * @param resultOutType Тип вывода результата (например, текстовый вывод или вывод в файл).
     * @return Писатель отчетов, соответствующий переданному типу вывода.
     */
    public ReportWriter createReportWriter(ResultOutType resultOutType) {
        return reportWriters.get(resultOutType);
    }
}
