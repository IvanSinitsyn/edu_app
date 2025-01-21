package ru.hoff.edu.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.hoff.edu.util.DataConverter;

@Getter
@Builder
@AllArgsConstructor
public class Parcel {

    private final String name;
    private final char[][] form;
    private final String symbol;

    @Setter
    private boolean isLoaded;

    public Integer getWidth() {
        return form[0].length;
    }

    public Integer getHeight() {
        return form.length;
    }

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

    public String showInfo() {
        return "id(name): \"" + name + "\"\nform:\n" + String.join("\n", DataConverter.convertFormToString(form));
    }
}
