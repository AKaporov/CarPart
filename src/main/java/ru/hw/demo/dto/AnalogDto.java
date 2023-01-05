package ru.hw.demo.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AnalogDto {
    /**
     * каталожный номер
     */
    private final String vendorCode;

    /**
     * наименование
     */
    private final String name;

    /**
     * рейтинг
     */
    private final double rating;

    /**
     * продавец
     */
    private final String vendor;

    /**
     * стоимость
     */
    private final double price;
}
