package ru.hw.demo.service.convert;

import ru.hw.demo.domain.CarPart;
import ru.hw.demo.dto.CarPartRecommendedDto;

import java.util.List;

/**
 * @author Artem
 * Интерфейс для конвертации CarPart в CarPartToRecommendedDto
 */
public interface ConvertCarPartToRecommendedDto {
    List<CarPartRecommendedDto> convertToRecommendedDto(List<CarPart> carPartList);
}
