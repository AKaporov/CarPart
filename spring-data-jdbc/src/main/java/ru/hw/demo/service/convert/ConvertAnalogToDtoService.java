package ru.hw.demo.service.convert;

import ru.hw.demo.domain.Analog;
import ru.hw.demo.dto.AnalogDto;

/**
 * @author Artem
 * Интерфейс для конвертации Analog в AnalogDto
 */

public interface ConvertAnalogToDtoService {
    AnalogDto convertToAnalogDto(Analog analog);
}
