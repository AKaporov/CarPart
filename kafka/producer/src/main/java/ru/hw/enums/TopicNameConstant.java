package ru.hw.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Artem
 * Константы для topic с которыми работает приложение
 */
@Getter
@RequiredArgsConstructor
public enum TopicNameConstant {
    // todo Для работы с несколькими topic надо сделать как-то более правильнее. Иначе получется, что topicName должно
    //  совпадать с application.kafka.topics

    CAR_PART_TOPIC("carpart-topic"),
    ANALOG_TOPIC("analog-topic");

    private final String topicName;
}
