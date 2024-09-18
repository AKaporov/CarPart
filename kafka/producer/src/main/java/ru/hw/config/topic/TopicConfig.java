package ru.hw.config.topic;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.kafka.config.TopicBuilder;
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
@PropertySource(value={"classpath:application.yml"})
public class TopicConfig {
    public final Set<String> topicNames = Collections.emptySet();
    //пример использования Enum в качестве Constant (по примеру из книги Чистый код Роберт Мартин)
    private TopicConstant PARTITION = TopicConstant.PARTITION;
    private TopicConstant REPLICA = TopicConstant.REPLICA;


//    public TopicConfig(@Value("${application.kafka.topics}") String topicNames) {
//        Set<String> topics = Pattern.compile(",")
//                .splitAsStream(topicNames)
//                .map(String::trim)
//                .collect(Collectors.toSet());
//
//        this.topicNames.addAll(topics);
//    }

    public TopicConfig(@Value("${my.property}") String topicNames) {
//        this.topicNames = topicNames;

        Set<String> topics = Pattern.compile(",")
                .splitAsStream("carpart-topic,analog-topic")
                .map(String::trim)
                .collect(Collectors.toSet());

        this.topicNames.addAll(topics);
    }

    @Value("${application.kafka.topics}")
    public void setTopicNames(String topicNames) {
        Set<String> topics = Pattern.compile(",")
                .splitAsStream("carpart-topic,analog-topic")
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
    public List<NewTopic> createKafkaTopics() {
        System.out.println("topicNames: " + topicNames);
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
}
