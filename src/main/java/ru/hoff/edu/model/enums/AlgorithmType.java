package ru.hoff.edu.model.enums;

/**
 * Перечисление, представляющее типы алгоритмов, которые могут быть использованы для загрузки посылок.
 */
public enum AlgorithmType {
    /**
     * Простой алгоритм загрузки.
     */
    EASY,

    /**
     * Алгоритм, который распределяет посылки равномерно.
     */
    EQUALLY,

    /**
     * Оптимальный алгоритм загрузки, обеспечивающий наилучшее использование пространства.
     */
    OPTIMAL;

    /**
     * Преобразует строку в соответствующее значение перечисления {@link AlgorithmType}.
     *
     * @param value Строковое представление типа алгоритма (например, "easy", "equally", "optimal").
     * @return Соответствующее значение перечисления {@link AlgorithmType}.
     * @throws IllegalArgumentException если переданное значение не соответствует ни одному из типов алгоритмов.
     */
    public static AlgorithmType fromString(String value) {
        try {
            return AlgorithmType.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unknown algorithm type: " + value, e);
        }
    }
}
