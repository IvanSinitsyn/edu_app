package ru.hoff.edu.service.filewriter;


import java.io.IOException;
import java.util.List;

/**
 * Интерфейс для записи данных в файл.
 * Определяет метод для записи списка данных в файл по указанному пути.
 *
 * @param <T> Тип данных, которые будут записаны в файл.
 */
public interface OutputFileWriter<T> {

    /**
     * Записывает данные в файл по указанному пути.
     *
     * @param filePath Путь к файлу, в который будут записаны данные.
     * @param data     Список данных для записи в файл.
     * @throws IOException если возникает ошибка ввода-вывода при записи в файл.
     */
    void write(String filePath, List<T> data) throws IOException;
}

