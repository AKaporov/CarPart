package ru.hw.config.topic;

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
public class TopicConfig {
    private static final Logger log = LoggerFactory.getLogger(TopicConfig.class);
    public final Set<String> topicNames = Collections.emptySet();

    public TopicConfig(@Value("${application.kafka.topics}") String topicNames) {
        Set<String> topics = Pattern.compile(",")
                .splitAsStream(topicNames)
                .map(String::trim)
                .collect(Collectors.toSet());

        this.topicNames.addAll(topics);
    }
}
