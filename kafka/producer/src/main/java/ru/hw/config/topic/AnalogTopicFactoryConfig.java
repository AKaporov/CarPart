package ru.hw.config.topic;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.ProducerFactory;

/**
 * @author Artem
 * Класс - конфигурации для отправки в kafka объекта тип Analog
 */
@Configuration
public class AnalogTopicFactoryConfig {
    @Bean
    public ProducerFactory<String, Analog> producerFactory() {

    }
}
