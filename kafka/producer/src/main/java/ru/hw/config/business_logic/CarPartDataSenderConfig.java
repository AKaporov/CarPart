package ru.hw.config.business_logic;

import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
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
public class CarPartDataSenderConfig {
    private static final Logger log = LoggerFactory.getLogger(CarPartDataSenderConfig.class);

    @Bean
    public DataSenderCarPart carPartDataSender(NewTopic carPartTopic, KafkaTemplate<String, CarPart> carPartKafkaTemplate) {
//        String carPartTopicName = getCarPartTopicName(createKafkaTopics);

        return new DataSenderCarPartImpl(carPartTopic.name(),
                carPartKafkaTemplate,
                carPart -> log.info("Работает обработчик события при успешной доставки данных до брокера \"Consumer\"! onAsked, carPart: {}", carPart));
    }

//    private String getCarPartTopicName(List<NewTopic> topicList) {
//        return topicList.stream()
//                .filter(t -> TopicNameConstant.CAR_PART_TOPIC.getTopicName().equalsIgnoreCase(t.name()))
//                .findFirst()
//                .orElseThrow(() -> new TopicNotFundException("Topic с именем "
//                        .concat(TopicNameConstant.CAR_PART_TOPIC.getTopicName())
//                        .concat(" не найден в application (application.kafka.topics)!!")))
//                .name();
//    }
}
