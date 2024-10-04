package ru.hw.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.hw.model.CarPart;
import ru.hw.service.kafka.DataSenderCarPart;
import ru.hw.service.value_source.CarPartValueSource;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class LauncherCommandImpl implements Command {
    private static final Logger log = LoggerFactory.getLogger(LauncherCommandImpl.class);
    private final DataSenderCarPart producer;
    private final CarPartValueSource valueSource;
    private final ScheduledExecutorService executorService;

    public LauncherCommandImpl(DataSenderCarPart producer, CarPartValueSource valueSource) {
        this.producer = producer;
        this.valueSource = valueSource;

        this.executorService = Executors.newScheduledThreadPool(1);
        log.info("Успешно создан \"Отправитель\" для отправки данных в kafka по расписанию");
    }

    @Override
    public void execute() {
        executorService.scheduleAtFixedRate(() -> {
            CarPart value = valueSource.generate();
            producer.send(value);
        }, 0, 1, TimeUnit.MINUTES);
    }
}
