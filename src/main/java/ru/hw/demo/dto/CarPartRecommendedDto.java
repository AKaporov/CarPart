package ru.hw.demo.dto;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * @author Artem
 * Рекомендуемая запасная часть автомобиля. Для отображения списка всех запасных частей
 */

@Builder
@Data
@RequiredArgsConstructor
public class CarPartRecommendedDto {
    private final long id;

    /**
     * каталожный номер
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