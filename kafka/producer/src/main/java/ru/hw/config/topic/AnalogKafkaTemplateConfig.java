package ru.hw.config.topic;

import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import ru.hw.model.Analog;

/**
 * @author Artem
 * Класс-конфигурации для создания реального producer-а для analog-topic
 */

@Configuration
public class AnalogKafkaTemplateConfig {

    /**
     * Bean создания producer-а через KafkaTemplate (дополнительная SpringBoot-ая абстракция стартера над натуральным producer-ом)
     *
     * @param analogProducerFactory producer для analog-topic
     * @return bean producer-а для analog-topic
     */
    public KafkaTemplate<String, Analog> kafkaTemplate(ProducerFactory<String, Analog> analogProducerFactory) {
        return new KafkaTemplate<>(analogProducerFactory);
    }
}
