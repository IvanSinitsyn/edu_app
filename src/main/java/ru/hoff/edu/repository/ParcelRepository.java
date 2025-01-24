package ru.hoff.edu.repository;

import org.springframework.stereotype.Component;
import ru.hoff.edu.domain.Parcel;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Класс-репозиторий для управления посылками (Parcel).
 * Предоставляет методы для добавления, редактирования, удаления и поиска посылок.
 */
@Component
public class ParcelRepository {

    private static final int START_OF_CHAR_SYMBOL = 0;
    private final List<Parcel> parcels;

    /**
     * Конструктор по умолчанию. Инициализирует репозиторий и добавляет начальный набор посылок.
     */
    public ParcelRepository() {
        parcels = new ArrayList<>();
        initializeParcels();
    }

    private void initializeParcels() {
        parcels.add(new Parcel("1", new char[][]{{'1'}}, "1", false));
        parcels.add(new Parcel("2", new char[][]{{'2', '2'}}, "2", false));
        parcels.add(new Parcel("3", new char[][]{{'3', '3', '3'}}, "3", false));
        parcels.add(new Parcel("4", new char[][]{{'4', '4', '4', '4'}}, "4", false));
        parcels.add(new Parcel("5", new char[][]{{'5', '5', '5', '5', '5'}}, "5", false));
        parcels.add(new Parcel("6", new char[][]{{'6', '6', '6'}, {'6', '6', '6'}, {'6', '6', '6'}}, "6", false));
        parcels.add(new Parcel("7", new char[][]{{'7', '7', '7', '7'}, {'7', '7', '7', ' '}}, "7", false));
        parcels.add(new Parcel("8", new char[][]{{'8', '8', '8', '8'}, {'8', '8', '8', '8'}}, "8", false));
        parcels.add(new Parcel("9", new char[][]{{'9', '9', '9'}, {'9', '9', '9'}, {'9', '9', '9'}}, "9", false));
    }

    /**
     * Добавляет новую посылку в репозиторий.
     *
     * @param parcel Посылка, которую необходимо добавить.
     */
    public void addParcel(Parcel parcel) {
        parcels.add(parcel);
    }

    /**
     * Редактирует существующую посылку по её идентификатору.
     *
     * @param id        Идентификатор посылки, которую необходимо отредактировать.
     * @param newName   Новое название посылки.
     * @param newForm   Новая форма посылки.
     * @param newSymbol Новый символ для отображения посылки.
     * @return Отредактированная посылка.
     */
    public Parcel edit(String id, String newName, char[][] newForm, String newSymbol) {
        Parcel newParcel = new Parcel(newName, newForm, newSymbol, false);
        deleteParcel(id);
        newParcel.redraw(newSymbol.charAt(START_OF_CHAR_SYMBOL));
        parcels.add(newParcel);
        return newParcel;
    }

    /**
     * Удаляет посылку по её названию.
     *
     * @param parcelName Название посылки, которую необходимо удалить.
     */
    public void deleteParcel(String parcelName) {
        parcels.removeIf(parcel -> parcel.getName().equals(parcelName));
    }

    /**
     * Возвращает список всех посылок в репозитории.
     *
     * @return Список всех посылок.
     */
    public List<Parcel> findAllParcels() {
        return new ArrayList<>(parcels);
    }

    /**
     * Находит посылку по её названию.
     *
     * @param name Название посылки, которую необходимо найти.
     * @return Optional, содержащий найденную посылку, или пустой Optional, если посылка не найдена.
     */
    public Optional<Parcel> findParcelByName(String name) {
        return parcels.stream()
                .filter(p -> p.getName().equals(name))
                .findFirst()
                .map(Parcel::new);
    }
}
