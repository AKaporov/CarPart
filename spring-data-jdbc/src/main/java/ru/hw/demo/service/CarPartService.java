package ru.hw.demo.service;

import org.springframework.data.domain.Page;
import ru.hw.demo.dto.CarPartFullInfoDto;
import ru.hw.demo.dto.CarPartRecommendedDto;
import ru.hw.demo.pojo.FilterCarPart;

import java.util.List;

public interface CarPartService {
    /**
     * @param vendorCode каталожный номер запчасти
     * @return возвращает найденную запчасть по её {@code vendorCode}
     */
    CarPartRecommendedDto getByVendorCode(String vendorCode);

    /**
     * @param filter фильтр поиска по марке, модели, году выпуска или двигатель.
     * @return возвращает список найденных запчастей по {@code filter}
     */
    Page<CarPartRecommendedDto> getByFilter(FilterCarPart filter);

    /**
     * @param vendorCode каталожный номер запчасти
     * @return возвращает расширенную информацию по {@code vendorCode}
     */
    CarPartFullInfoDto getExtendedInfoByVendorCode(String vendorCode);
}
