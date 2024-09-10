package ru.hw.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CarPart {
    private long id;
    private String vendorCode;
    private String sku;
    private String name;
    private String description;
    private double price;
    private String manufacturer;
    private double rating;
    private Brand brand;
    private Model model;
    private Engine engine;
    private Country country;
    private List<Photo> photoList;
    private List<Analog> analogList;
}
