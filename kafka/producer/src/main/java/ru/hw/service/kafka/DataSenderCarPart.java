package ru.hw.service.kafka;

import ru.hw.model.CarPart;

/**
 * @author Artem
 * Интерфейс для отпавки данных в topic. Общий для отправки всех сообщений
 */
public interface DataSenderCarPart {
    // todo Переписать на использование generic вместо конкретного типа CarPart
    void send(CarPart value);
}
