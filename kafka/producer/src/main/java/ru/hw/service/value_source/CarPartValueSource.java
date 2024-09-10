package ru.hw.service.value_source;

import ru.hw.model.CarPart;

/**
 * @author Artem
 * Интерфейс для создания данных для отправки в topic
 */
public interface CarPartValueSource {
    CarPart generate();
}
