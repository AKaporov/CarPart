package ru.hw.demo.service.convert;

import ru.hw.demo.domain.CarPart;
import ru.hw.demo.dto.CarPartFullInfoDto;

/**
 * @author Artem
 * Интерфейс для конвертации CarPart в CarPartFullInfoDto
 */

public interface ConvertCarPartToFullInfoDtoService {
    CarPartFullInfoDto convertToFullInfoDto(CarPart carPart);
}
