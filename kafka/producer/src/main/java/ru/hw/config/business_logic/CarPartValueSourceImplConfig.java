//package ru.hw.config.business_logic;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import ru.hw.service.kafka.DataSenderCarPart;
//import ru.hw.service.value_source.CarPartValueSourceImpl;
//
///**
// * @author Artem
// * Класс-конфигурации для создания bean CarPartValueSourceImpl
// */
//
//@Configuration
//public class CarPartValueSourceImplConfig {
//
//    @Bean
//    public CarPartValueSourceImpl carPartValueSourceImpl(DataSenderCarPart carPartDataSender) {
//        return new CarPartValueSourceImpl(carPartDataSender);
//    }
//
//}
