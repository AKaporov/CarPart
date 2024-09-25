package ru.hw.service.value_source;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hw.model.Brand;
import ru.hw.model.CarPart;
import ru.hw.model.Country;
import ru.hw.model.Engine;
import ru.hw.service.kafka.DataSenderCarPart;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class CarPartValueSourceImpl implements CarPartValueSource {
    private static final Logger log = LoggerFactory.getLogger(CarPartValueSourceImpl.class);
    private final DataSenderCarPart producer;
    private final AtomicLong nexValueId = new AtomicLong(1);
    private final ScheduledExecutorService executorService;
    private CarPart valueToSend;

    public CarPartValueSourceImpl(DataSenderCarPart producer) {
        this.producer = producer;

        this.executorService = Executors.newScheduledThreadPool(1);
        log.info("Успешно создан \"Отправитель\" для отправки данных в kafka по расписанию");
    }

    @Override
    public void generate() {
        sendValueToKafka();
    }

    private void sendValueToKafka() {
        executorService.scheduleAtFixedRate(() -> {
                    setNewValueToSendKafka();
                    producer.send(valueToSend);
                },
                0,
                1,
                TimeUnit.MINUTES
        );
    }

    private void setNewValueToSendKafka() {
        valueToSend = getNewCarPartToSendKafka();
    }

    private CarPart getNewCarPartToSendKafka() {
        var id = getNewId();
        return CarPart.builder()
                .id(id)
                .vendorCode("VendorCode_".concat(Long.toString(id)))
                .sku("SKU_".concat(Long.toString(id)))
                .name("CarPartName_".concat(Long.toString(id)))
                .brand(new Brand(id, "brandName_".concat(Long.toString(id))))
                .engine(new Engine(id, "engineName_".concat(Long.toString(id))))
                .country(new Country(id, "country_".concat(Long.toString(id))))
                .build();
    }

    private long getNewId() {
        return nexValueId.getAndIncrement();
    }
}
