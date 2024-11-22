package ru.hw.model;

import lombok.*;

import java.util.List;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
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
