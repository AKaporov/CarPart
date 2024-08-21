# В проекте (на основе Spring-Data-JPA) применяется Spring Kafka

Будем использовать Kafka через docker (см. [docker-compose.yml](docker%2Fdocker-compose.yml)). 

Проект состоит из модулей **consumer** (получатель) и модуля **producer** (отправитель). Topic kafka для передачи 
автозапчастей называется "**carpart-topic**", для аналогов называется "**analog-topic**".  Все topic kafka находятся в 
группе "**car-part-kafka-group**" (указывается в application.kafka.topic). Producer отправляет сообщения в topic kafka, 
consumer получает по пачкам (настройка **ConsumerConfig.MAX_POLL_RECORDS_CONFIG**). Оправка и получение данных в формате JSON.
Полученные объекты в БД не сохраняются.

Kafka-ui, от команды provectus - это инструмент для визуализации данных Kafka.

Настройки **[application.properties](\kafka\producer\src\main\resources\application.properties)** для раздела spring.kafka хранятся в фале KafkaProperties.class (файл можно найти через поиск).
В application указываются настройки, которые могут меняться от запускаемого стенда, а в файлах-config 
(например AnalogTopicFactoryConfig.java) указываются Константы сервиса. Поэтому логично, что настройки в двух местах.

Правила:
- **Один** consumer в **Одной** группе! Consumer не может быть больше чем partition (быть больше может, но использовать не получится).

todo:
1) сделать несколько групп, что бы пощупать работу с partition (задать ключ партиции и т.д.)


## Ссылки
* [Apache Kafka](https://kafka.apache.org/)
* [Настройки kafka через application](https://docs.spring.io/spring-boot/docs/current/reference/html/application-properties.html#application-properties.integration.spring.kafka.admin.auto-create)
* [Руководство по настройке Apache Kafka с помощью Docker (Bueldung)](https://www.baeldung.com/ops/kafka-docker-setup)
* [Kafka UI краткий гайд(Habr)](https://habr.com/ru/articles/753398/)
* [Работа с Apache Kafka в приложениях на Spring Boot, часть 1](https://www.youtube.com/watch?v=9FikRH8rXas)
* [Работа с Apache Kafka в приложениях на Spring Boot, часть 2](https://www.youtube.com/watch?v=Y-ClxJozvCo)
* [Введение в Apache Kafka с Spring (Baeldung)](https://www.baeldung.com/spring-kafka)
* [Пример Сергея Петрелевича на GitHub](https://github.com/AKaporov/jvm-digging/tree/master/kafka-spring)
