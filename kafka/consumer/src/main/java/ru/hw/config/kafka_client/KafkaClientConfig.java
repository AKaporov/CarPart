//package ru.hw.config.kafka_client;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import ru.hw.config.listener.CarPartKafkaListenerConfig;
//import ru.hw.service.CarPartConsumerService;
//
///**
// * @author Artem
// * Конфигурация всех Bean для KafkaClient
// */
//@Configuration
//public class KafkaClientConfig {
//
//    @Bean
//    public CarPartKafkaListenerConfig carPartKafkaClient(CarPartConsumerService carPartConsumerService) {
//        return new CarPartKafkaListenerConfig(carPartConsumerService);
//    }
//}
