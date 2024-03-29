package ru.hw.demo.service.convert;

import ru.hw.demo.domain.CarPart;
import ru.hw.demo.dto.CarPartRecommendedDto;

/**
 * @author Artem
 * Интерфейс для конвертации CarPart в CarPartToRecommendedDto
 */
public interface ConvertCarPartToRecommendedDtoService {
    CarPartRecommendedDto convertToRecommendedDto(CarPart carPart);
}