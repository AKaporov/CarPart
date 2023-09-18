package ru.hw.demo.config;

import org.junit.jupiter.api.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.hw.demo.domain.*;
import ru.hw.demo.repository.*;

import java.util.List;

@Configuration
public class CommandLineRunnerConfigurator {

    public static final String BRAND_GAZ = "Gaz";
    public static final String BRAND_URAL = "Ural";
    public static final String BRAND_KAMAZ = "Kamaz";
//    @Autowired
//    private static BrandRepository brandRepository;

//    @Bean
//    @Order(1)
//    public CommandLineRunner brandsCommandLineRunner(BrandRepository brandRepository) {
//        return args -> {
//            brandRepository.save(Brand.builder().name(BRAND_KAMAZ).build());
//            brandRepository.save(Brand.builder().name(BRAND_URAL).build());
//            brandRepository.save(Brand.builder().name(BRAND_GAZ).build());
//
//        };
//    }
//
//    @Bean
//    @Order(2)
//    public CommandLineRunner modelsCommandLineRunner(ModelRepository modelRepository) {
//        return args -> {
//            modelRepository.save(Model.builder().name("Gaz-66").yearRelease(1964).build());
//            modelRepository.save(Model.builder().name("Ural-4320").yearRelease(1977).build());
//            modelRepository.save(Model.builder().name("Kamaz 65201").yearRelease(2000).build());
//        };
//    }
//
//    @Bean
//    @Order(3)
//    public CommandLineRunner enginesCommandLineRunner(EngineRepository repository) {
//        return args -> {
//            repository.save(Engine.builder().name("Diesel").build());
//            repository.save(Engine.builder().name("Petrol").build());
//            repository.save(Engine.builder().name("Turbocharged diesel").build());
//        };
//    }
//
//    @Bean
//    @Order(4)
//    public CommandLineRunner countriesCommandLineRunner(CountryRepository repository) {
//        return args -> {
//            repository.save(Country.builder().name("Russia").build());
//        };
//    }
//
//    @Bean
//    @Order(5)
//    public CommandLineRunner carPartsCommandLineRunner(CarPartRepository repository) {
//        return args -> {
//            carPartLoader(repository);
//        };
//    }
//
//    private static void carPartLoader(CarPartRepository carPartRepository) {
//        List<Brand> brands = brandRepository.findAll();
//
//        Brand brandGaz = brands.stream()
//                .filter(brand -> BRAND_GAZ.equalsIgnoreCase(brand.getName()))
//                .findFirst()
//                .get();
//
//        CarPart save = carPartRepository.save(CarPart.builder()
//                .vendorCode("GZ-750Z370-S")
//                .sku("SKU-201902-0057")
//                .name("ДИСК СЦЕПЛЕНИЯ")
//                .description("Диск сцепления передает крутящий момент от ДВС к трансмиссии, выступает в качестве составляющей трения.")
//                .price(59720.66d)
//                .manufacturer("ZF SACHS")
//                .rating(9.7d)
//                .brand(brandGaz)
//                .build());
//
//
//        System.out.println("****** KAPOROV = " + save);
//    }
}
