package ru.hw.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@ToString
public class CarPart {
    private long id;
    private String vendorCode;
    private String sku;
    private String name;
    private Brand brand;
    private Engine engine;
    private Country country;
    private List<Photo> photoList;
    private List<Analog> analogList;
}
