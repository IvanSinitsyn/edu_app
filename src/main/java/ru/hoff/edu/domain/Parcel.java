package ru.hoff.edu.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Класс, представляющий посылку (Parcel) с именем, формой и символом.
 * Форма посылки представлена двумерным массивом символов.
 * Класс предоставляет методы для получения размеров посылки, перерисовки формы и отображения информации.
 */
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Setter
@Getter
@Builder
public class Parcel {

    private String name;
    private char[][] form;
    private String symbol;
    private boolean isLoaded;

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

    public char[][] getForm() {
        return form;
    }

    public void setForm(char[][] form) {
        this.form = form;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public boolean getIsLoaded() {
        return isLoaded;
    }

    public void setIsLoaded(boolean isLoaded) {
        this.isLoaded = isLoaded;
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
}
