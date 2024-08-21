package ru.hw.config.topic;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import ru.hw.config.ObjectMapperConfig;
import ru.hw.model.CarPart;

import java.util.Map;

/**
 * @author Artem
 * Класс - конфигурации для отправки в kafka объекта типа CarPart.
 * Этот bean создается стартером невидимо для нас, но для кастомизации default параметров стартера его приходится
 * переопределять вручную и явно прописать использоваие ObjectMapper
 */
@Configuration
public class CarPartTopicFactoryConfig {
    private static final Logger log = LoggerFactory.getLogger(CarPartTopicFactoryConfig.class);

    @Bean
    public ProducerFactory<String, CarPart> producerFactory(KafkaProperties kafkaProperties, ObjectMapperConfig objectMapperConfig) {
        Map<String, Object> props = kafkaProperties.buildProducerProperties();
        // serializable для Ключа
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        // serializable для Значения
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        // что бы стартер использовал ObjectMapper который мы определили, а не созданный по-умолчанию, нужно:
        var carPartKafkaProducerFactory = new DefaultKafkaProducerFactory<String, CarPart>(props);
        carPartKafkaProducerFactory.setValueSerializer(new JsonSerializer<>(objectMapperConfig.objectMapper()));
        return carPartKafkaProducerFactory;
    }
}
