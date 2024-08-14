package ru.hw.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.JacksonUtils;

/**
 * @author Artem
 * Класс - конфигурации сериализации объекта отправляемого в kafka и десериализовать после получения из kafka.
 * В данном примере в виде JSON
 * <p>
 * Сериализация - это процесс сохранения состояния объекта в последовательность байт;
 * Десериализация - это процесс восстановсления объекта из этих байт.
 */

@Configuration
public class ObjectMapperConfig {
    @Bean
    public ObjectMapper objectMapper() {
        return JacksonUtils.enhancedObjectMapper();
    }
}
