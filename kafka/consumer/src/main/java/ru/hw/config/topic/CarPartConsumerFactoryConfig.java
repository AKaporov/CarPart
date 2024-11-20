package ru.hw.config.topic;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import ru.hw.model.CarPart;

import java.util.Map;

import static org.springframework.kafka.support.serializer.JsonDeserializer.TYPE_MAPPINGS;

/**
 * @author Artem
 * Класс - конфигурации для работы с объектом типа CarPart в carpart-topic.
 * <p>
 * Этот bean создается стартером невидимо для нас, но для кастомизации default параметров приходится переопределять
 * вручную и явно прописать использоваие ObjectMapper/
 */

@Configuration
public class CarPartConsumerFactoryConfig {
    /**
     * Переопределение default consumer-а для carpart-topic.
     *
     * @param kafkaProperties    свойства kafka
     * @param objectMapperConfig правила mapping объекта
     * @return доработанный consumer для получения сообщения с использованием нашего ObjectMapper
     */
    @Bean
    public ConsumerFactory<String, CarPart> carPartConsumerFactory(KafkaProperties kafkaProperties,
                                                                   ObjectMapper objectMapperConfig) {
        Map<String, Object> props = kafkaProperties.buildConsumerProperties();
//        С точки зрения приложения параметра Deserializer всегда будет константы (для всех сред, где будет запускаться
//        сервис). Поэтому логично, что они указаны тут, а не в application

        // Deserializer для Ключа
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        // Deserializer для Значения
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);

        // Внутри стартера есть ограничение (типа Security) на использование Deserializer-ом данных, которые он знает.
        // Про наш custom-ый класс Deserializer ничего не знает. Поэтому расскажем Deserializer-у о наших данных
        props.put(TYPE_MAPPINGS, "ru.hw.model.CarPart:ru.hw.model.CarPart");

        // Для Прома нужно указать большее количество. Для dev-профиля подойдет значение три, что бы видеть, что
        // получение идет пачками
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 3);

        // Важный параметр, чтобы понимать живой ли consumer или нет (Out of memory, или всё еще обрабатывает
        // сообщения из последней полученной пачки). Если не будет consumer, то брокер сделает ReBalance - а это
        // дополнительная нагрузка. Значение должно быть больше, чем в factory.getContainerProperties().setIdleBetweenPolls()
        props.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, 3_000);

        // что бы наш стартер использовал ObjectMapper который мы определили, а не созданный по-умолчанию, нужно:
        var topicConsumerFactory = new DefaultKafkaConsumerFactory<String, CarPart>(props);
        topicConsumerFactory.setValueDeserializer(new JsonDeserializer<>(objectMapperConfig));
        return topicConsumerFactory;
    }
}
