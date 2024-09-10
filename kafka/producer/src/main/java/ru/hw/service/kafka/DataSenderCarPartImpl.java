package ru.hw.service.kafka;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.hw.model.CarPart;

import java.util.Objects;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class DataSenderCarPartImpl implements DataSenderCarPart {
    private static final Logger log = LoggerFactory.getLogger(DataSenderCarPartImpl.class);

    private final String topicName;
    private final KafkaTemplate<String, CarPart> kafkaTemplate;
    private final Consumer<CarPart> sendAsk;

    @Override
    public void send(CarPart value) {
        try {
            log.info("try send value: {}", value);
            kafkaTemplate.send(topicName, value)
                    .whenComplete((result, ex) -> {
                        if (Objects.isNull(ex)) {
                            log.info("Ураааа, удалось доставить ДО БРОКЕРА вот эту запчасть с id = {}, его offset: {}", value.getId(), result.getRecordMetadata().offset());

                            sendAsk.accept(value);  // Вызов обработчика! Если мы хотим что-то сделать после успешного доставления сообщения до Брокера. Например, проставить признак на платежке "Доставлено до брокера"
                        } else {
                            log.error("Error!! CarPart with id: {} Ну не смогла доставить сообщение до брокера...........", value.getId());
                        }

                    });

        } catch (Exception ex) {
            log.error("!!! Exception !!! Send error, value: {}", value);
        }
    }
}
