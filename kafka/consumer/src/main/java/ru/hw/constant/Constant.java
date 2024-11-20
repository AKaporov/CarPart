package ru.hw.constant;

public class Constant {
    private Constant() {
    }

    /**
     * <code>my_service.kafka.topics.topic_1.name</code>
     */
    public static final String MY_SERVICE_KAFKA_TOPICS_TOPIC_1_NAME_CONFIG = "${my_service.kafka.topics.topic_1.name}";
    private static final String MY_SERVICE_KAFKA_TOPICS_TOPIC_1_NAME_DOC = "Настройка в которой указывается название " +
            "первого топика";

    /**
     * <code>my_service.kafka.topics.topic_2.name</code>
     */
    public static final String MY_SERVICE_KAFKA_TOPICS_TOPIC_2_NAME_CONFIG = "${my_service.kafka.topics.topic_2.name}";
    private static final String MY_SERVICE_KAFKA_TOPICS_TOPIC_2_NAME_DOC = "Настройка в которой указывается название " +
            "второго топика";


    /**
     * <code>spring.kafka.consumer.group-id</code>
     */
    public static final String SPRING_KAFKA_CONSUMER_GROUP_ID_CONFIG = "${spring.kafka.consumer.group-id}";
    private static final String SPRING_KAFKA_CONSUMER_GROUP_ID_DOC = "Настройка application в которой указывается " +
            "группа для топиков";

    /**
     * <code>carPartListenerContainerFactory</code>
     */
    public static final String CAR_PART_LISTENER_CONTAINER_FACTORY_CONFIG = "carPartListenerContainerFactory";
    private static final String CAR_PART_LISTENER_CONTAINER_FACTORY_DOC = "Название bean который отвечает за создание " +
            "consumer-а для " + MY_SERVICE_KAFKA_TOPICS_TOPIC_1_NAME_CONFIG + ". К одному factory можно привязать несколько topic-ов.";
}
