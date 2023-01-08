package ru.hw.demo.pojo;

import lombok.Builder;
import lombok.Data;

/**
 * @author Artem
 * Класс для параметров фильтра - марка, модель, год выпуска или двигатель.
 */

@Builder
@Data
public class FilterCarPart {
    /**
     * Наименование марки
     */
    private final String brandName;
    /**
     * Наименование модели
     */
    private final String modelName;
    /**
     * Год выпуска
     */
    private final Integer yearRelease;

    /**
     * Наименование двигателя
     */
    private final String engineName;
}
