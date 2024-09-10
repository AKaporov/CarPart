package ru.hw.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import ru.hw.model.CarPart;
import ru.hw.service.kafka.DataSenderCarPart;
import ru.hw.service.value_source.CarPartValueSource;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RunnerApplication implements CommandLineRunner {
    private final static Logger log = LoggerFactory.getLogger(RunnerApplication.class);

    private final CarPartValueSource valueSource;
    private final DataSenderCarPart producer;

    private CarPart valueToSend;

    @Override
    public void run(String... args) throws Exception {
        setNewValueToSend();
        sendNewValuesToKafka();

        log.info("generate started.");
    }

    private void setNewValueToSend() {
        valueToSend = valueSource.generate();
    }

    private void sendNewValuesToKafka() {
        var executorService = Executors.newScheduledThreadPool(1);
        executorService.scheduleAtFixedRate(() -> producer.send(valueToSend), 0, 1, TimeUnit.MINUTES);
    }
}
