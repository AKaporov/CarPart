package ru.hw.demo.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class CarPartFullInfoDto {
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
     * описание
     */
    private final String description;

    /**
     * стоимость
     */
    private final double price;

    /**
     * производитель
     */
    private final String manufacturer;

    /**
     * рейтинг
     */
    private final double rating;

    /**
     * марка
     */
    private final String brandName;

    /**
     * модель
     */
    private final String modelName;

    /**
     * двигатель
     */
    private final String engineName;

    /**
     * страна производства
     */
    private final String countryName;

    /**
     * фотографии
     */
    private final List<PhotoDto> photoList;

    /**
     * аналоги
     */
    private final List<AnalogDto> analogDtoList;
}
