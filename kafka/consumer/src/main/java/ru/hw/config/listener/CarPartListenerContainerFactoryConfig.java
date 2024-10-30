package ru.hw.config.listener;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.scheduling.concurrent.ConcurrentTaskExecutor;
import ru.hw.model.CarPart;

/**
 * @author Artem
 * Класс - конфигурации для настройки слушателя топика
 */
@Configuration
public class CarPartListenerContainerFactoryConfig {

    @Bean("carPartListenerContainerFactory")
//    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, StringValue>>
//    listenerContainerFactory(ConsumerFactory<String, StringValue> consumerFactory) {
    public ConcurrentKafkaListenerContainerFactory<String, CarPart>
    carPartListenerContainerFactory(ConsumerFactory<String, CarPart> consumerFactory) {
        var factory = new ConcurrentKafkaListenerContainerFactory<String, CarPart>();
        factory.setConsumerFactory(consumerFactory);
        factory.setBatchListener(Boolean.TRUE);
        factory.setConcurrency(1);
        factory.getContainerProperties().setIdleBetweenPolls(1_000);
        factory.getContainerProperties().setPollTimeout(1_000);

        SimpleAsyncTaskExecutor executor = new SimpleAsyncTaskExecutor("car-part-consumer-");
        executor.setConcurrencyLimit(10);

        ConcurrentTaskExecutor listenerTaskExecutor = new ConcurrentTaskExecutor(executor);
        factory.getContainerProperties().setListenerTaskExecutor(listenerTaskExecutor);

        return factory;
    }

}
