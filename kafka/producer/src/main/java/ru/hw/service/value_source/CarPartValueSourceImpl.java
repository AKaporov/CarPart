package ru.hw.service.value_source;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hw.model.Brand;
import ru.hw.model.CarPart;
import ru.hw.model.Engine;

import java.util.concurrent.atomic.AtomicLong;

@Service
public class CarPartValueSourceImpl implements CarPartValueSource {
    private static final Logger log = LoggerFactory.getLogger(CarPartValueSourceImpl.class);
    private AtomicLong nexValueId = new AtomicLong(1);


    @Override
    public CarPart generate() {
        var id = getNewId();
        return CarPart.builder()
                .id(id)
                .brand(new Brand(id, "brandName_".concat(Long.toString(id))))
                .engine(new Engine(id, "engineName_".concat(Long.toString(id))))
                .build();
    }

    private long getNewId() {
        return nexValueId.getAndIncrement();
    }
}
