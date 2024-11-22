package ru.hw.config.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import ru.hw.constant.Constant;
import ru.hw.model.CarPart;
import ru.hw.service.CarPartConsumerService;

import java.util.List;

/**
 * @author Artem
 * Для отделения конфигурирования от бизнес-логики подписку на topic делаем тут, а обработку выносим в CarPartConsumer.
 * При таком подходе обращение к application и использование будет в одном месте. Например, использование названия topic-а, bean-а
 */
@Configuration
public class CarPartKafkaListenerConfig {
    public static final Logger log = LoggerFactory.getLogger(CarPartKafkaListenerConfig.class);
    private final CarPartConsumerService consumer;

    public CarPartKafkaListenerConfig(CarPartConsumerService consumer) {
        this.consumer = consumer;
    }

    @KafkaListener(
            topics = Constant.MY_SERVICE_KAFKA_TOPICS_TOPIC_1_NAME_CONFIG,
            containerFactory = Constant.CAR_PART_LISTENER_CONTAINER_FACTORY_CONFIG,  // Создает consumer-ы который обрабатываем. К одному factory можно привязать несколько topic-ов.
            groupId = Constant.SPRING_KAFKA_CONSUMER_GROUP_ID_CONFIG
    )
    public void carPartListener(@Payload List<CarPart> values) {
        log.info("Ураааа,я получил пачку сообщений от broker!!!! Размер пачки:{}", values.size());
        consumer.accept(values);
    }
}
