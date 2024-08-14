package ru.hw.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author Artem
 * Основной класс - конфигурации:
 * 1) для injection topic-ов kafka для работы
 */

@Configuration
public class KafkaProducerConfig {
    private static final Logger log = LoggerFactory.getLogger(KafkaProducerConfig.class);
    public final Set<String> topicNames = Collections.emptySet();

    public KafkaProducerConfig(@Value("${application.kafka.topics}") String topicNames) {
        Set<String> topics = Pattern.compile(",")
                .splitAsStream(topicNames)
                .map(String::trim)
                .collect(Collectors.toSet());

        this.topicNames.addAll(topics);
    }
}
