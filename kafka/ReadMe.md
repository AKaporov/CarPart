# В проекте (на основе Spring-Data-JPA) применяется Spring Kafka

Будем использовать Kafka через docker. 
Проект состоит из модулей consumer (получатель) и модуля producer (отправитель). 
Producer отправляет сообщения в topic kafka, consumer получает по пачкам (настройка ConsumerConfig.MAX_POLL_RECORDS_CONFIG).
Topic Kafka указывается в application.

## Ссылки
* [Apache Kafka](https://kafka.apache.org/)
* [Работа с Apache Kafka в приложениях на Spring Boot, часть 1](https://www.youtube.com/watch?v=9FikRH8rXas)
* [Работа с Apache Kafka в приложениях на Spring Boot, часть 2](https://www.youtube.com/watch?v=Y-ClxJozvCo)
* [Введение в Apache Kafka с Spring (Baeldung)](https://www.baeldung.com/spring-kafka)
* [Пример Сергея Петрелевича](https://github.com/AKaporov/jvm-digging/tree/master/kafka-spring)