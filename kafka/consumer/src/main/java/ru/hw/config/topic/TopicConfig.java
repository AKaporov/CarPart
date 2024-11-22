package ru.hw.config.topic;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import ru.hw.constant.Constant;
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
    private final Set<String> topicNames;

    public TopicConfig(@Value(Constant.MY_SERVICE_KAFKA_TOPICS_TOPIC_1_NAME_CONFIG) String carPartTopicName,
                       @Value(Constant.MY_SERVICE_KAFKA_TOPICS_TOPIC_2_NAME_CONFIG) String analogPartTopicName) {
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
        return topicNames.stream()
                .map(name -> TopicBuilder
                        .name(name)
                        .partitions(TopicConstant.PARTITION.count())
                        .replicas(TopicConstant.REPLICA.count())
                        .build()
                )
                .toList();
    }
}

