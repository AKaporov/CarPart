package ru.hw.demo.service;

import ru.hw.demo.dto.CarPartFullInfoDto;
import ru.hw.demo.dto.CarPartRecommendedDto;
import ru.hw.demo.pojo.FilterCarPart;

import java.util.List;
import java.util.Optional;

public interface CarPartService {
    /**
     * @param vendorCode каталожный номер
     * @return возвращает найденную запчасть по её {@code vendorCode}, иначе Optional.empty()
     */
    Optional<CarPartRecommendedDto> getByVendorCode(String vendorCode);

    /**
     * @param filter фильтр поиска по марке, модели, году выпуска или двигатель.
     * @return возвращает список найденных запчастей по {@code filter}
     */
    List<CarPartRecommendedDto> getByFilter(FilterCarPart filter);

    /**
     * @param cpPreviewDto предварительный просмотр запчасти
     * @return возвращает дополнительную информацию по {@code cpPreviewDto}
     */
    Optional<CarPartFullInfoDto> getAddInfoById(CarPartRecommendedDto cpPreviewDto);
}
