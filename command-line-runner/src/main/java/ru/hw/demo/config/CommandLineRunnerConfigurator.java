package ru.hw.demo.config;

import org.junit.jupiter.api.Order;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import ru.hw.demo.domain.*;
import ru.hw.demo.enums.BrandType;
import ru.hw.demo.enums.CountryType;
import ru.hw.demo.enums.EngineType;
import ru.hw.demo.enums.ModelType;
import ru.hw.demo.repository.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Configuration
public class CommandLineRunnerConfigurator {

    @Bean
    @Order(1)
    public CommandLineRunner brandsCommandLineRunner(BrandRepository repository) {
        return args -> {
            repository.save(Brand.builder().name(BrandType.KAMAZ.getName()).build());
            repository.save(Brand.builder().name(BrandType.URAL.getName()).build());
            repository.save(Brand.builder().name(BrandType.GAZ.getName()).build());

        };
    }

    @Bean
    @Order(2)
    public CommandLineRunner modelsCommandLineRunner(ModelRepository repository) {
        return args -> {
            repository.save(Model.builder()
                    .name(ModelType.GAZ_66_1964.getName())
                    .yearRelease(ModelType.GAZ_66_1964.getYearRelease())
                    .build());
            repository.save(Model.builder()
                    .name(ModelType.URAL_4320_1977.getName())
                    .yearRelease(ModelType.URAL_4320_1977.getYearRelease())
                    .build());
            repository.save(Model.builder()
                    .name(ModelType.KAMAZ_65201_2000.getName())
                    .yearRelease(ModelType.KAMAZ_65201_2000.getYearRelease())
                    .build());
        };
    }

    @Bean
    @Order(3)
    public CommandLineRunner enginesCommandLineRunner(EngineRepository repository) {
        return args -> {
            repository.save(Engine.builder().name(EngineType.DIESEL.getName()).build());
            repository.save(Engine.builder().name(EngineType.PETROL.getName()).build());
            repository.save(Engine.builder().name(EngineType.TURBOCHARGED_DIESEL.getName()).build());
        };
    }

    @Bean
    @Order(4)
    public CommandLineRunner countriesCommandLineRunner(CountryRepository repository) {
        return args -> {
            repository.save(Country.builder().name(CountryType.RUSSIA.getName()).build());
        };
    }

    @Bean
    @Order(5)
    public CommandLineRunner photoCommandLineRunner(PhotoRepository repository) {
        return args -> {
            List<Photo> photos = new ArrayList<>(8);

            photos.add(Photo.builder().photoUrl("https://localhost:8080/carpart/1/#&gid=1&pid=1").build());
            photos.add(Photo.builder().photoUrl("https://localhost:8080/carpart/1/#&gid=1&pid=2").build());
            photos.add(Photo.builder().photoUrl("https://localhost:8080/carpart/1/#&gid=1&pid=3").build());
            photos.add(Photo.builder().photoUrl("https://localhost:8080/carpart/2/#&gid=1&pid=1").build());
            photos.add(Photo.builder().photoUrl("https://localhost:8080/carpart/2/#&gid=1&pid=2").build());
            photos.add(Photo.builder().photoUrl("https://localhost:8080/carpart/3/#&gid=1&pid=1").build());
            photos.add(Photo.builder().photoUrl("https://localhost:8080/carpart/4/#&gid=1&pid=1").build());
            photos.add(Photo.builder().photoUrl("https://localhost:8080/carpart/6/#&gid=1&pid=1").build());

            repository.saveAllAndFlush(photos);
        };
    }

    @Bean
    @Order(Ordered.LOWEST_PRECEDENCE)
    public CommandLineRunner carPartsCommandLineRunner(CarPartRepository carPartRepository,
                                                       BrandRepository brandRepository,
                                                       ModelRepository modelRepository,
                                                       EngineRepository engineRepository,
                                                       CountryRepository countryRepository,
                                                       PhotoRepository photoRepository,
                                                       AnalogRepository analogRepository) {
        return args -> {
            carPartLoader(carPartRepository, brandRepository, modelRepository, engineRepository,
                    countryRepository, photoRepository, analogRepository);
            analogLoader(analogRepository, carPartRepository);
        };
    }

    private void analogLoader(AnalogRepository analogRepository, CarPartRepository carPartRepository) {
        List<Analog> analogs = new ArrayList<>(4);

        Optional<CarPart> carPartGZ_511 = carPartRepository.findByVendorCode("GZ-511.1601130-280");
        carPartGZ_511.ifPresent(carPart -> analogs.add(Analog.builder()
                .vendor("No name (China)")
                .carPart(carPart)
                .build()));

//                    .ifPresent(carPart -> analogs.add(Analog.builder()
//                            .vendor("No name (China)")
//                            .carPart(carPart)
//                            .build()));

        Optional<CarPart> carPartKmz740 = carPartRepository.findByVendorCode("KMZ-740.60-1008025-20");
        carPartKmz740.ifPresent(carPart -> analogs.add(Analog.builder()
                .vendor("KAMAZ (Russia)")
                .carPart(carPart)
                .build()));
//                    .ifPresent(carPart -> analogs.add(Analog.builder()
//                            .vendor("KAMAZ (Russia)")
//                            .carPart(carPart)
//                            .build()));

        Optional<CarPart> carPartKmz3937478 = carPartRepository.findByVendorCode("KMZ-3937478-2");
        carPartKmz3937478.ifPresent(carPart ->
                analogs.add(Analog.builder()
                        .vendor("Cummins (China)")
                        .carPart(carPart)
                        .build())
        );
//                    .ifPresent(carPart -> analogs.add(Analog.builder()
//                            .vendor("Cummins (China)")
//                            .carPart(carPart)
//                            .build()));

        Optional<CarPart> carPartUrl4320 = carPartRepository.findByVendorCode("URL-4320-020");
        carPartUrl4320.ifPresent(carPart ->
                analogs.add(Analog.builder()
                        .vendor("URAL (Russia)")
                        .carPart(carPart)
                        .build())
        );
//                    .ifPresent(carPart -> analogs.add(Analog.builder()
//                            .vendor("URAL (Russia)")
//                            .carPart(carPart)
//                            .build()));

        List<Analog> analogsSaved = analogRepository.saveAllAndFlush(analogs);

        List<CarPart> carParts = new ArrayList<>();
        carParts.add(linkCarPartToAnalog(carPartGZ_511, List.of(analogsSaved.get(0))));
        carParts.add(linkCarPartToAnalog(carPartKmz740, List.of(analogsSaved.get(1), analogsSaved.get(2))));
        carParts.add(linkCarPartToAnalog(carPartUrl4320, List.of(analogsSaved.get(3))));
        carPartRepository.saveAllAndFlush(carParts);

//        carPartGZ_511.ifPresent(carPart -> {
//            carPart.setAnalogList(List.of(analogsSaved.get(0)));
//            carPartRepository.save(carPart);
//        });
//
//        carPartKmz740.ifPresent(carPart -> {
//            carPart.setAnalogList(List.of(analogsSaved.get(1)));
//            carPartRepository.save(carPart);
//        });
//
//        carPartKmz3937478.ifPresent(carPart -> {
//            carPart.setAnalogList(List.of(analogsSaved.get(2)));
//            carPartRepository.save(carPart);
//        });
//
//        carPartUrl4320.ifPresent(carPart -> {
//            carPart.setAnalogList(List.of(analogsSaved.get(3)));
//            carPartRepository.save(carPart);
//        });
    }

    private CarPart linkCarPartToAnalog(Optional<CarPart> carPartOptional, List<Analog> analogs) {
        return carPartOptional.map(carPart -> {
                            carPart.setAnalogList(analogs);
                            return carPart;
                        }
                )
                .orElse(CarPart.builder().build());
    }

    private void carPartLoader(CarPartRepository carPartRepository,
                               BrandRepository brandRepository,
                               ModelRepository modelRepository,
                               EngineRepository engineRepository,
                               CountryRepository countryRepository,
                               PhotoRepository photoRepository,
                               AnalogRepository analogRepository) {

        List<Brand> brands = brandRepository.findAll();
        Brand brandKamaz = getBrandByType(brands, BrandType.KAMAZ);
        Brand brandUral = getBrandByType(brands, BrandType.URAL);
        Brand brandGaz = getBrandByType(brands, BrandType.GAZ);

        List<Model> models = modelRepository.findAll();
        Model modelGaz66_1964 = getModelByType(models, ModelType.GAZ_66_1964);
        Model modelUral4320_1977 = getModelByType(models, ModelType.URAL_4320_1977);
        Model modelKamaz65301_2000 = getModelByType(models, ModelType.KAMAZ_65201_2000);

        List<Engine> engines = engineRepository.findAll();
        Engine engineDiesel = getEngineByType(engines, EngineType.DIESEL);
        Engine enginePetrol = getEngineByType(engines, EngineType.PETROL);
        Engine engineTurboDiesel = getEngineByType(engines, EngineType.TURBOCHARGED_DIESEL);

        List<Country> countries = countryRepository.findAll();
        Country countryRussia = getCountryByType(countries, CountryType.RUSSIA);

        List<Photo> photos = photoRepository.findAll();
        Photo photoOne_One = photos.get(0);
        Photo photoOne_Two = photos.get(1);
        Photo photoOne_Three = photos.get(2);


        CarPart carPartOne = CarPart.builder()
                .brand(brandGaz)
                .model(modelGaz66_1964)
                .engine(engineDiesel)
                .country(countryRussia)
                .vendorCode("GZ-750Z370-S")
                .sku("SKU-201902-0057")
                .name("ДИСК СЦЕПЛЕНИЯ")
                .description("Диск сцепления передает крутящий момент от ДВС к трансмиссии, выступает в качестве составляющей трения.")
                .price(59720.66d)
                .manufacturer("ZF SACHS")
                .rating(9.7d)
                .photoList(List.of(photoOne_One, photoOne_Two, photoOne_Three))
                .build();

        CarPart savedCarPartOne = carPartRepository.save(carPartOne);

//        Analog analogCarPartOne = Analog.builder().vendor("No name (China)").carPart(savedCarPartOne).build();
//        savedCarPartOne.setAnalogList(List.of(analogCarPartOne));
        System.out.println("****** KAPOROV = " + savedCarPartOne);
//
//        CarPart save1 = carPartRepository.save(savedCarPartOne);
//        System.out.println("****** KAPOROV = " + save1);

        List<CarPart> all = carPartRepository.findAll();
        all.stream().forEach(System.out::println);
    }

    private Country getCountryByType(List<Country> countries, CountryType countryType) {
        return countries.stream()
                .filter(country -> countryType.getName().equals(country.getName()))
                .findFirst()
                .orElseGet(() -> Country.builder().build());
    }

    private Engine getEngineByType(List<Engine> engines, EngineType engineType) {
        return engines.stream()
                .filter(engine -> engineType.getName().equals(engine.getName()))
                .findFirst()
                .orElseGet(() -> Engine.builder().build());
    }

    private Model getModelByType(List<Model> models, ModelType modelType) {
        return models.stream()
                .filter(model -> modelType.getName().equals(model.getName()))
                .filter(model -> modelType.getYearRelease() == model.getYearRelease())
                .findFirst()
                .orElseGet(() -> Model.builder().build());
    }

    private Brand getBrandByType(List<Brand> brands, BrandType brandType) {
        return brands.stream()
                .filter(brand -> brandType.getName().equals(brand.getName()))
                .findFirst()
                .orElseGet(() -> Brand.builder().build());
    }
}
