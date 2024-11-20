package ru.hw.service;

import ru.hw.model.CarPart;

import java.util.List;

/**
 * @author Artem
 * Интерфейс для бизнес-логики обработки сообщения из topic carpart
 */
public interface CarPartConsumerService {
    void accept(List<CarPart> values);
}
