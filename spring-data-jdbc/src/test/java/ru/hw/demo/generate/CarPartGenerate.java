//package ru.hw.demo.generate;
//
//import ru.hw.demo.domain.*;
//
//import java.util.ArrayList;
//
//public class CarPartGenerate {
//
//    public static CarPart getUaz446(Long id) {
//
//        CarPart carPart = CarPart.builder()
//                .brand(Brand.builder().name("UAZ").build())
//                .model(Model.builder().name("UAZ-469").yearRelease(1972).build())
//                .engine(Engine.builder().name("Turbocharged petrol").build())
//                .country(Country.builder().name("Morocco").build())
//                .photoList(new ArrayList<>(1))
//                .analogList(new ArrayList<>(1))
//                .name("Bridge UAZ-469 Military Front")
//                .description("Bridge UAZ-469 Military Front. Guarantee from 6 months.")
//                .manufacturer("Ulyanovsk Motor Plant")
//                .price(45_000)
//                .sku("202212-2103238563")
//                .vendorCode("UAZ-469-01")
//                .rating(4.8)
//                .build();
//
//        if (id != null) {
//            carPart.setIdTest(id);
//        }
//
//        return carPart;
//    }
//
//    public static CarPart getMoskvich2141(Long id) {
//        CarPart carPart = CarPart.builder()
//                .brand(Brand.builder().name("Moskvich").build())
//                .model(Model.builder().name("Moskvich 2141").yearRelease(2000).build())
//                .engine(Engine.builder().name("Petrol Turbocharged").build())
//                .country(Country.builder().name("Lithuania").build())
//                .photoList(null)
//                .analogList(null)
//                .name("Trunk lid for Moskvich 2141")
//                .description("Auto parsing.")
//                .manufacturer("\"SIGMA\", LITHUANIAN SOFTWARE")
//                .price(3_000)
//                .sku("202212-2610130591")
//                .vendorCode("MSK-2141-01")
//                .rating(0)
//                .build();
//
//        if (id != null) {
//            carPart.setIdTest(id);
//        }
//
//        return carPart;
//    }
//
//}
