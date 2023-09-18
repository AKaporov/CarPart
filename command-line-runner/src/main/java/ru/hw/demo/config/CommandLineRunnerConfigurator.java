package ru.hw.demo.config;

import org.junit.jupiter.api.Order;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.hw.demo.constant.MainConstant;
import ru.hw.demo.domain.*;
import ru.hw.demo.repository.BrandRepository;
import ru.hw.demo.repository.CarPartRepository;
import ru.hw.demo.repository.EngineRepository;
import ru.hw.demo.repository.ModelRepository;

import java.util.List;

@Configuration
public class CommandLineRunnerConfigurator {

    @Bean
    @Order(1)
    public CommandLineRunner brandsCommandLineRunner(BrandRepository brandRepository) {
        return args -> {
            brandRepository.save(Brand.builder().name(MainConstant.BRAND_KAMAZ).build());
            brandRepository.save(Brand.builder().name(MainConstant.BRAND_URAL).build());
//            brandRepository.save(Brand.builder().name(MainConstant.BRAND_GAZ).build());

        };
    }

    @Bean
    @Order(2)
    public CommandLineRunner modelsCommandLineRunner(ModelRepository modelRepository) {
        return args -> {
//            modelRepository.save(Model.builder().name("Gaz-66").yearRelease(1964).build());
            modelRepository.save(Model.builder().name("Ural-4320").yearRelease(1977).build());
            modelRepository.save(Model.builder().name("Kamaz 65201").yearRelease(2000).build());
        };
    }

    @Bean
    @Order(3)
    public CommandLineRunner enginesCommandLineRunner(EngineRepository repository) {
        return args -> {
//            repository.save(Engine.builder().name("Diesel").build());
            repository.save(Engine.builder().name("Petrol").build());
            repository.save(Engine.builder().name("Turbocharged diesel").build());
        };
    }

//    @Bean
//    @Order(4)
//    public CommandLineRunner countriesCommandLineRunner(CountryRepository repository) {
//        return args -> {
//            repository.save(Country.builder().name("Russia").build());
//        };
//    }

    @Bean
    @Order(5)
    public CommandLineRunner carPartsCommandLineRunner(CarPartRepository carPartRepository, BrandRepository brandRepository) {
        return args -> {
            carPartLoader(carPartRepository, brandRepository);
        };
    }

    private static void carPartLoader(CarPartRepository carPartRepository, BrandRepository brandRepository) {
//        List<Brand> brands = brandRepository.findAll();
//
//        Brand brandGaz = brands.stream()
//                .filter(brand -> MainConstant.BRAND_GAZ.equals(brand.getName()))
//                .findFirst()
//                .get();

        CarPart carPartOne = CarPart.builder()
//                .brand(brandGaz)
                .brand(Brand.builder().name(MainConstant.BRAND_GAZ).build())
                .model(Model.builder().name("Gaz-66").yearRelease(1964).build())
                .engine(Engine.builder().name("Diesel").build())
                .country(Country.builder().name("Russia").build())
                .vendorCode("GZ-750Z370-S")
                .sku("SKU-201902-0057")
                .name("ДИСК СЦЕПЛЕНИЯ")
                .description("Диск сцепления передает крутящий момент от ДВС к трансмиссии, выступает в качестве составляющей трения.")
                .price(59720.66d)
                .manufacturer("ZF SACHS")
                .rating(9.7d)
                .photoList(
                        List.of(
                                Photo.builder().photoUrl("https://localhost:8080/carpart/1/#&gid=1&pid=1").build(),
                                Photo.builder().photoUrl("https://localhost:8080/carpart/1/#&gid=1&pid=2").build(),
                                Photo.builder().photoUrl("https://localhost:8080/carpart/1/#&gid=1&pid=3").build())
                )
                .build();

        CarPart build = CarPart.builder()
//                .brand(brandGaz)
                .brand(Brand.builder().name(MainConstant.BRAND_GAZ).build())
                .model(Model.builder().name("Gaz-66").yearRelease(1964).build())
                .engine(Engine.builder().name("Diesel").build())
                .country(Country.builder().name("Russia").build())
                .vendorCode("GZ-750Z370-S")
                .sku("SKU-201902-0057")
                .name("ДИСК СЦЕПЛЕНИЯ")
                .description("Диск сцепления передает крутящий момент от ДВС к трансмиссии, выступает в качестве составляющей трения.")
                .price(59720.66d)
                .manufacturer("ZF SACHS")
                .rating(9.7d)
                .photoList(
                        List.of(
                                Photo.builder().photoUrl("https://localhost:8080/carpart/1/#&gid=1&pid=1").build(),
                                Photo.builder().photoUrl("https://localhost:8080/carpart/1/#&gid=1&pid=2").build(),
                                Photo.builder().photoUrl("https://localhost:8080/carpart/1/#&gid=1&pid=3").build())
                )
                .build();

        CarPart savedCarPartOne = carPartRepository.save(carPartOne);
//        CarPart savedCarPartTwo = carPartRepository.save(build);
//        System.out.println("****** KAPOROV = " + savedCarPartTwo);

        Analog analogCarPartOne = Analog.builder().vendor("No name (China)").carPart(savedCarPartOne).build();
        savedCarPartOne.setAnalogList(List.of(analogCarPartOne));

        CarPart save1 = carPartRepository.save(savedCarPartOne);
        System.out.println("****** KAPOROV = " + savedCarPartOne);
        System.out.println("****** KAPOROV = " + save1);
    }
}
