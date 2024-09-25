package ru.hw.config.topic;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import ru.hw.model.CarPart;

/**
 * @author Artem
 * Класс - конфигурации для создания реального producer для carpart-topic
 */

@Configuration
public class CarPartKafkaTemplateConfig {
    /**
     * Bean создания реального producer-а через KafkaTemplate (дополнительная SpringBoot-ая абстракция стартера над натуральным producer-ом)
     *
     * @param carPartProducerFactory producer для carpart-topic
     * @return bean producer-а carpart-topic.
     */
    @Bean
    public KafkaTemplate<String, CarPart> carPartKafkaTemplate(ProducerFactory<String, CarPart> carPartProducerFactory) {
        return new KafkaTemplate<>(carPartProducerFactory);
    }
}
