package ru.hw.config.topic;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
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
 * Класс - конфигурации для работы с объектом типа CarPart в carpart-topic.
 * <p>
 * Этот bean создается стартером невидимо для нас, но для кастомизации default параметров приходится переопределять
 * вручную и явно прописать использоваие ObjectMapper/
 */
@Configuration
public class CarPartProducerFactoryConfig {
    /**
     * Переопределение default producer-а для carpart-topic.
     *
     * @param kafkaProperties    свойства kafka
     * @param objectMapperConfig правила mapping объекта
     * @return доработанный producer для отправки сообщения с использованием нашего ObjectMapper
     */
    @Bean
    public ProducerFactory<String, CarPart> carPartProducerFactory(KafkaProperties kafkaProperties,
                                                                   ObjectMapperConfig objectMapperConfig) {
        Map<String, Object> props = kafkaProperties.buildProducerProperties();
//        С точки зрения приложения gараметра serializable всегда будет константы (для всех сред, где будет запускаться
//        сервис). Поэтому логично, что они указаны тут, а не в application
        // serializable для Ключа
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        // serializable для Значения
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        // что бы наш стартер использовал ObjectMapper который мы определили, а не созданный по-умолчанию, нужно:
        var topicProducerFactory = new DefaultKafkaProducerFactory<String, CarPart>(props);
        topicProducerFactory.setValueSerializer(new JsonSerializer<>(objectMapperConfig.objectMapper()));
        return topicProducerFactory;
    }
}
