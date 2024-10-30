package ru.hw.config.topic;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import ru.hw.enums.TopicConstant;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @author Artem
 * Основной класс - конфигурации:
 * 1) для injection topic-ов kafka для работы
 */
@Configuration
public class TopicConfig {
    //пример использования Enum в качестве Constant (по примеру из книги Чистый код Роберт Мартин)
    private TopicConstant PARTITION = TopicConstant.PARTITION;
    private TopicConstant REPLICA = TopicConstant.REPLICA;

    private final Set<String> topicNames;
    private final String carPartTopicName;
    private final String analogPartTopicName;

    public TopicConfig(@Value("${my_service.kafka.topics.topic_1.name}") String carPartTopicName,
                       @Value("${my_service.kafka.topics.topic_2.name}") String analogPartTopicName) {
        this.carPartTopicName = carPartTopicName;
        this.analogPartTopicName = analogPartTopicName;

        var topics = Set.of(carPartTopicName, analogPartTopicName);
        this.topicNames = Collections.unmodifiableSet(topics);
    }

    /**
     * Создание topic-ов по указанному списку в application.kafka.topics
     *
     * @return созданные topic-и
     */
    @Bean
    public List<NewTopic> createKafkaTopics() {
        List<NewTopic> topics = topicNames.stream()
                .map(name -> TopicBuilder
                        .name(name)
                        .partitions(PARTITION.count())
                        .replicas(REPLICA.count())
                        .build()
                )
                .toList();

//        return new KafkaAdmin.NewTopics(topics.toArray(NewTopic[]::new));
        return topics;
    }

    @Bean
    public NewTopic carPartTopic() {
        return TopicBuilder
                .name(carPartTopicName)
                .partitions(PARTITION.count())
                .replicas(REPLICA.count())
                .build();
    }

    @Bean
    public NewTopic analogPartTopic() {
        return TopicBuilder
                .name(analogPartTopicName)
                .partitions(PARTITION.count())
                .replicas(REPLICA.count())
                .build();
    }
}

