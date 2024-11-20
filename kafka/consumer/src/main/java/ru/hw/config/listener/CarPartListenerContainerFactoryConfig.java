package ru.hw.config.listener;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.scheduling.concurrent.ConcurrentTaskExecutor;
import ru.hw.constant.Constant;
import ru.hw.model.CarPart;

/**
 * @author Artem
 * Класс - конфигурации фаюрики для настройки слушателя топика для его дальнейшего создания
 */
@Configuration
public class CarPartListenerContainerFactoryConfig {

    @Bean(Constant.CAR_PART_LISTENER_CONTAINER_FACTORY_CONFIG)
//    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, CarPart>>
//    listenerContainerFactory(ConsumerFactory<String, CarPart> consumerFactory) {
    public ConcurrentKafkaListenerContainerFactory<String, CarPart>
    carPartListenerContainerFactory(ConsumerFactory<String, CarPart> consumerFactory) {
        var factory = new ConcurrentKafkaListenerContainerFactory<String, CarPart>();
        factory.setConsumerFactory(consumerFactory);

        // Получение пачками иначе listener (carPartListener) будет прилучать по одному сообщению (default)
        factory.setBatchListener(Boolean.TRUE);

        // Штука тонкая и вызывает массу вопросов. В целом consumer !!!потокоНЕбезопасный!!! т.е. нельзя consumer kafka
        // дергать из нескольких потоков. Но с другой стороны может быть ситуация, когда подписываемся на несколько
        // topic-ов и в каждом topic-е по несколько partition (например 5 topic-ов по 5 partition на каждом topic,
        // итого 25 потенциальных listener-ов, потенциальных consumer-ов может быть). Тогда для масштабирования можно
        // сделать: 1) Если позволяют мощности, то 25 instance запущенных приложений; 2) Если ресурсы ограничены и
        // масштабироваться нужно thread-ми, то kafka-starter предлагает опцию concurrency (для нашего примера можно указать 25)
        factory.setConcurrency(1);

        // Странная настройка, которая влияет на интервал между вызовами poll, если partition еще не назначены. Значение
        // не должно быть больше, чем указано в ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG
        factory.getContainerProperties().setIdleBetweenPolls(1_000);

        // Время ожидания сообщения в kafka т.е. после подключения к kafka, если в ней нет сообщения, то будем ждать
        // указанное время .... вдруг появится ). Если сообщение есть, то это время не ждем
        factory.getContainerProperties().setPollTimeout(1_000);

        SimpleAsyncTaskExecutor executor = new SimpleAsyncTaskExecutor("car-part-consumer-");
        executor.setConcurrencyLimit(10);

        ConcurrentTaskExecutor listenerTaskExecutor = new ConcurrentTaskExecutor(executor);
        factory.getContainerProperties().setListenerTaskExecutor(listenerTaskExecutor);

        return factory;
    }

    /**
     * Для отделения конфигурирования от бизнес-логики подписку на topic делаем тут, а обработку выносим в CarPartConsumer.
     * При таком подходе обращение к application и использование будет в одном месте. Например, использование названия topic-а, bean-а
     */
//    public static class CarPartKafkaClient {
//        public static final Logger log = LoggerFactory.getLogger(CarPartKafkaClient.class);
//        private final CarPartConsumer consumer;
//
//        public CarPartKafkaClient(CarPartConsumer consumer) {
//            this.consumer = consumer;
//        }
//
//        @KafkaListener(
//                topics = Constant.MY_SERVICE_KAFKA_TOPICS_TOPIC_1_NAME_CONFIG,
//                containerFactory = Constant.CAR_PART_LISTENER_CONTAINER_FACTORY_CONFIG,  // создает consumer-ы который обрабатываем
//                groupId = Constant.SPRING_KAFKA_CONSUMER_GROUP_ID_CONFIG
//        )
//        // по default будет одно сообщение
//        public void carPartListener(@Payload List<CarPart> values) {
//            log.info("Ураааа, что-то получили от broker!!!! value.size():{}", values.size());
//            consumer.accept(values);
//        }
//    }
}
