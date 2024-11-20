package ru.hw.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hw.model.CarPart;

import java.util.List;

@Service
public class CarPartConsumerServiceImpl implements CarPartConsumerService {
    private static final Logger LOG = LoggerFactory.getLogger(CarPartConsumerServiceImpl.class);

    @Override
    public void accept(List<CarPart> values) {
        values.forEach(value -> LOG.info("Вот это сообщение получил:{}", value));
    }
}
