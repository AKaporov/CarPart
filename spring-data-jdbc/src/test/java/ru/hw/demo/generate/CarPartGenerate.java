package ru.hw.demo.generate;

import lombok.RequiredArgsConstructor;
import ru.hw.demo.domain.CarPart;

import java.util.HashSet;
import java.util.Objects;

@RequiredArgsConstructor
public class CarPartGenerate {
    public static CarPart getUaz446(Long id) {

        CarPart carPart = CarPart.builder()
                .id(null)
                .brandRef(null)
                .modelRef(null)
                .engineRef(null)
                .countryRef(null)
                .photos(new HashSet<>(1))
                .analogs(new HashSet<>(1))
                .name("Bridge UAZ-469 Military Front")
                .description("Bridge UAZ-469 Military Front. Guarantee from 6 months.")
                .manufacturer("Ulyanovsk Motor Plant")
                .price(45_000d)
                .sku("202212-2103238563")
                .vendorCode("UAZ-469-01")
                .rating(4.8d)
                .build();

        if (Objects.nonNull(id)) {
            carPart.setId(id);
        }

        return carPart;
    }

    public static CarPart getMoskvich2141(Long id) {
        CarPart carPart = CarPart.builder()
                .id(null)
                .brandRef(null)
                .modelRef(null)
                .engineRef(null)
                .countryRef(null)
                .photos(null)
                .analogs(null)
                .name("Trunk lid for Moskvich 2141")
                .description("Auto parsing.")
                .manufacturer("\"SIGMA\", LITHUANIAN SOFTWARE")
                .price(3_000d)
                .sku("202212-2610130591")
                .vendorCode("MSK-2141-01")
                .rating(0d)
                .build();

        if (Objects.nonNull(id)) {
            carPart.setId(id);
        }

        return carPart;
    }

}
