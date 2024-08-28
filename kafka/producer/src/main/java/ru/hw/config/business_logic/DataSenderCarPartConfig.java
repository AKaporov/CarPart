package ru.hw.config.business_logic;

import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import ru.hw.model.CarPart;
import ru.hw.service.kafka.DataSenderCarPart;
import ru.hw.service.kafka.DataSenderCarPartImpl;


/**
 * @author Artem
 * Класс - конфигурации бизнес логики отправки данных типа CarPart
 */
@Configuration
public class DataSenderCarPartConfig {
    private static final Logger log = LoggerFactory.getLogger(DataSenderCarPartConfig.class);

    public DataSenderCarPart dataSender(NewTopic topic, KafkaTemplate<String, CarPart> kafkaTemplate) {
        return new DataSenderCarPartImpl(topic.name(),
                kafkaTemplate,
                value -> log.info("asked, value: {}", value));
    }
}
