package ru.hw.service.value_source;

import org.springframework.stereotype.Service;
import ru.hw.model.Brand;
import ru.hw.model.CarPart;
import ru.hw.model.Country;
import ru.hw.model.Engine;

import java.util.concurrent.atomic.AtomicLong;

@Service
public class CarPartValueSourceImpl implements CarPartValueSource {
    private final AtomicLong nexValueId = new AtomicLong(1);

    @Override
    public CarPart generate() {
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
