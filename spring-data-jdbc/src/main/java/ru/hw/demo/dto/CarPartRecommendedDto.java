package ru.hw.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * @author Artem
 * Рекомендуемая запасная часть автомобиля. Для отображения списка всех запасных частей
 */

@Builder
@Data
@AllArgsConstructor
public class CarPartRecommendedDto {
    private final long id;

    /**
     * Каталожный номер
     */
    private final String vendorCode;

    /**
     * артикул
     */
    private final String sku;

    /**
     * наименование
     */
    private final String name;

    /**
     * стоимость
     */
    private final double price;

    /**
     * рейтинг
     */
    private final double rating;
}
