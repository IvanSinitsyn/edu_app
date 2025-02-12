package ru.hoff.edu.service.core;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.hoff.edu.domain.Parcel;
import ru.hoff.edu.domain.Truck;
import ru.hoff.edu.model.entity.ParcelEntity;
import ru.hoff.edu.repository.ParcelRepository;
import ru.hoff.edu.service.exception.ParcelNotFoundException;
import ru.hoff.edu.service.mapper.ParcelMapper;
import ru.hoff.edu.validation.ParcelValidator;

import java.util.List;

/**
 * Сервис для работы с посылками.
 * Предоставляет методы для добавления, поиска, удаления и редактирования посылок,
 * а также для размещения посылок в грузовиках.
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class ParcelService {

    private final ParcelRepository parcelRepository;
    private final ParcelMapper parcelMapper;
    private final ParcelValidator parcelValidator;

    /**
     * Добавляет новую посылку в репозиторий.
     *
     * @param parcel Посылка, которую необходимо добавить.
     * @throws IllegalArgumentException если посылка с таким именем уже существует или форма посылки невалидна.
     */
    public void add(Parcel parcel) {
        if (parcelRepository.existsById(parcel.getName())) {
            throw new IllegalArgumentException("Посылка с таким именем уже существует");
        }

        if (!parcelValidator.isParcelFormValid(parcel.getForm(), parcel.getSymbol().charAt(0))) {
            throw new IllegalArgumentException("Форма посылки невалидная");
        }

        parcel.redraw(parcel.getSymbol().charAt(0));
        parcelRepository.save(parcelMapper.toEntity(parcel));
    }

    /**
     * Возвращает список всех посылок.
     *
     * @return Список всех посылок.
     */
    public Page<Parcel> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ParcelEntity> parcelEntities = parcelRepository.findAll(pageable);
        return parcelEntities.map(parcelMapper::fromEntity);
    }

    /**
     * Ищет посылку по её названию.
     *
     * @param name Название посылки.
     * @return Optional, содержащий найденную посылку, или пустой Optional, если посылка не найдена.
     */
    public Parcel findByName(String name) {
        ParcelEntity parcel = parcelRepository.findById(name).orElseThrow(() -> new ParcelNotFoundException("Посылка " + name + " не найдена"));
        return parcelMapper.fromEntity(parcel);
    }

    /**
     * Удаляет посылку по её названию.
     *
     * @param name Название посылки, которую необходимо удалить.
     */
    public void delete(String name) {
        parcelRepository.deleteById(name);
    }

    /**
     * Редактирует существующую посылку.
     *
     * @param id        Идентификатор посылки.
     * @param newName   Новое название посылки.
     * @param newForm   Новая форма посылки.
     * @param newSymbol Новый символ для отображения посылки.
     * @return Отредактированная посылка.
     * @throws IllegalArgumentException если посылка не найдена или новая форма невалидна.
     */
    public Parcel edit(String id, String newName, char[][] newForm, String newSymbol) {
        ParcelEntity existedParcel = parcelRepository.findById(id).orElseThrow(() -> new ParcelNotFoundException("Посылка " + id + "не найдена"));

        if (!parcelValidator.isParcelFormValid(newForm, existedParcel.getSymbol().charAt(0))) {
            throw new IllegalArgumentException("Новая форма невалидная");
        }

        if (!id.equals(newName)) {
            ParcelEntity newParcel = new ParcelEntity(newName, parcelMapper.convertArrayToString(newForm), newSymbol, false);
            parcelRepository.delete(existedParcel);
            parcelRepository.save(newParcel);
            return parcelMapper.fromEntity(newParcel);
        } else {
            existedParcel.setForm(parcelMapper.convertArrayToString(newForm));
            existedParcel.setSymbol(newSymbol);
            ParcelEntity updatedParcel = parcelRepository.save(existedParcel);
            return parcelMapper.fromEntity(updatedParcel);
        }
    }

    /**
     * Ищет подходящий грузовик для размещения посылки.
     *
     * @param trucks Список грузовиков.
     * @param parcel Посылка, которую необходимо разместить.
     * @return Подходящий грузовик или {@code null}, если подходящий грузовик не найден.
     */
    public Truck findSuitableTruck(List<Truck> trucks, Parcel parcel) {
        for (Truck truck : trucks) {
            if (canFitInHalfTruck(truck, parcel)) {
                return truck;
            }
        }
        return null;
    }

    /**
     * Проверяет, можно ли разместить посылку в грузовике.
     *
     * @param truck  Грузовик, в который необходимо разместить посылку.
     * @param parcel Посылка, которую необходимо разместить.
     * @return {@code true}, если посылку можно разместить, иначе {@code false}.
     */
    public boolean tryPlacePackageInTruck(Truck truck, Parcel parcel) {
        for (int y = 0; y <= truck.getHeight() - parcel.getHeight(); y++) {
            for (int x = 0; x <= truck.getWidth() - parcel.getWidth(); x++) {
                if (truck.canPlace(parcel, x, y)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Проверяет, может ли посылка поместиться в грузовик, не превышая половину его вместимости.
     *
     * @param truck  Грузовик, в который необходимо разместить посылку.
     * @param parcel Посылка, которую необходимо разместить.
     * @return {@code true}, если посылка может поместиться, иначе {@code false}.
     */
    private boolean canFitInHalfTruck(Truck truck, Parcel parcel) {
        int potentialNewLoad = truck.getCurrentLoad() + parcel.getWidth() * parcel.getHeight();
        return potentialNewLoad <= truck.getHalfCapacity() && tryPlacePackageInTruck(truck, parcel);
    }

    /**
     * Размещает посылку в грузовике.
     *
     * @param truck  Грузовик, в который необходимо разместить посылку.
     * @param parcel Посылка, которую необходимо разместить.
     */
    public void placeParcelInTruck(Truck truck, Parcel parcel) {
        for (int y = 0; y <= truck.getHeight() - parcel.getHeight(); y++) {
            for (int x = 0; x <= truck.getWidth() - parcel.getWidth(); x++) {
                if (truck.canPlace(parcel, x, y)) {
                    truck.place(parcel, x, y);
                    return;
                }
            }
        }
    }
}
