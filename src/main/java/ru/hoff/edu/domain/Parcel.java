package ru.hoff.edu.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.hoff.edu.util.DataConverter;

import java.util.Arrays;

/**
 * Класс, представляющий посылку (Parcel) с именем, формой и символом.
 * Форма посылки представлена двумерным массивом символов.
 * Класс предоставляет методы для получения размеров посылки, перерисовки формы и отображения информации.
 */
@Getter
@Builder
@AllArgsConstructor
public class Parcel {

    private final String name;
    private final char[][] form;
    private final String symbol;

    @Setter
    private boolean isLoaded;

    /**
     * Конструктор копирования. Создает новый объект Parcel на основе существующего.
     *
     * @param other Объект Parcel, который необходимо скопировать.
     */
    public Parcel(Parcel other) {
        this.name = other.name;
        this.form = Arrays.copyOf(other.form, other.form.length); // Копирование массива
        this.symbol = other.symbol;
        this.isLoaded = other.isLoaded;
    }

    /**
     * Возвращает ширину посылки, вычисляемую как длина первой строки массива формы.
     *
     * @return Ширина посылки.
     */
    public Integer getWidth() {
        return form[0].length;
    }

    /**
     * Возвращает высоту посылки, вычисляемую как количество строк в массиве формы.
     *
     * @return Высота посылки.
     */
    public Integer getHeight() {
        return form.length;
    }

    /**
     * Перерисовывает форму посылки, заменяя все символы (кроме пробелов) на указанный символ.
     *
     * @param symbol Символ, которым будет заменено содержимое формы.
     */
    public void redraw(char symbol) {
        for (int i = 0; i < form.length; i++) {
            for (int j = 0; j < form[i].length; j++) {
                if (form[i][j] == ' ') {
                    continue;
                }

                form[i][j] = symbol;
            }
        }
    }

    /**
     * Возвращает строку с информацией о посылке, включая её имя и форму.
     *
     * @return Строка с информацией о посылке.
     */
    public String showInfo() {
        return "id(name): \"" + name + "\"\nform:\n" + String.join("\n", DataConverter.convertFormToString(form));
    }
}
