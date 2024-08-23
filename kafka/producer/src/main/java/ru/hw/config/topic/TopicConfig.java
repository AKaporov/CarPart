package ru.hw.config.topic;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;
import ru.hw.enums.TopicConstant;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author Artem
 * Основной класс - конфигурации:
 * 1) для injection topic-ов kafka для работы
 */

@Configuration
public class TopicConfig {
    public final Set<String> topicNames = Collections.emptySet();

    public TopicConfig(@Value("${application.kafka.topics}") String topicNames) {
        Set<String> topics = Pattern.compile(",")
                .splitAsStream(topicNames)
                .map(String::trim)
                .collect(Collectors.toSet());

        this.topicNames.addAll(topics);
    }

    /**
     * Создание topic-ов по указанному списку в application.kafka.topics
     *
     * @return созданные topic-и
     */
    @Bean
    public KafkaAdmin.NewTopics createKafkaTopics() {

        List<NewTopic> topics = topicNames.stream()
                .map(name -> TopicBuilder
                        .name(name)
                        .partitions(TopicConstant.PARTITION.count())
                        .replicas(TopicConstant.REPLICA.count())
                        .build())
                .toList();

        return new KafkaAdmin.NewTopics(topics.toArray(NewTopic[]::new));

    }
}
