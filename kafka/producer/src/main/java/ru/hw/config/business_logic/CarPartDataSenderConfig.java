package ru.hw.config.business_logic;

import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import ru.hw.enums.TopicNameConstant;
import ru.hw.exception.TopicNotFundException;
import ru.hw.model.CarPart;
import ru.hw.service.kafka.DataSenderCarPart;
import ru.hw.service.kafka.DataSenderCarPartImpl;

import java.util.List;


/**
 * @author Artem
 * Класс - конфигурации бизнес логики отправки данных типа CarPart
 */
@Configuration
public class CarPartDataSenderConfig {
    private static final Logger log = LoggerFactory.getLogger(CarPartDataSenderConfig.class);

    @Bean
    public DataSenderCarPart carPartDataSender(List<NewTopic> createKafkaTopics, KafkaTemplate<String, CarPart> carPartkafkaTemplate) {
        String carPartTopicName = getCarPartTopicName(createKafkaTopics);

        return new DataSenderCarPartImpl(carPartTopicName,
                carPartkafkaTemplate,
                carPart -> log.info("Event! onAsked, carPart: {}", carPart));
    }

    private String getCarPartTopicName(List<NewTopic> createKafkaTopics) {
        return createKafkaTopics.stream()
                .filter(t -> TopicNameConstant.CAR_PART_TOPIC.getTopicName().equalsIgnoreCase(t.name()))
                .findFirst()
                .orElseThrow(() -> new TopicNotFundException("Topic с именем "
                        .concat(TopicNameConstant.CAR_PART_TOPIC.getTopicName())
                        .concat(" не найден в application (application.kafka.topics)!!")))
                .name();
    }
}
