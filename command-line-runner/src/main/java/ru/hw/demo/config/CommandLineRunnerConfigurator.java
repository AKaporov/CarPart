package ru.hw.demo.config;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Order;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import ru.hw.demo.domain.*;
import ru.hw.demo.enums.*;
import ru.hw.demo.repository.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Configuration
public class CommandLineRunnerConfigurator {

    private static final String KAMAZ_RUSSIA = "KAMAZ (Russia)";

    @Bean
    @Order(1)
    public CommandLineRunner brandsCommandLineRunner(BrandRepository repository) {
        return args -> {
            List<Brand> brands = List.of(
                    Brand.builder().name(BrandType.KAMAZ.getName()).build(),
                    Brand.builder().name(BrandType.URAL.getName()).build(),
                    Brand.builder().name(BrandType.GAZ.getName()).build()
            );

            repository.saveAllAndFlush(brands);
        };
    }

    @Bean
    @Order(2)
    public CommandLineRunner modelsCommandLineRunner(ModelRepository repository) {
        return args -> {
            List<Model> models = new ArrayList<>(3);
            models.add(Model.builder()
                    .name(ModelType.GAZ_66_1964.getName())
                    .yearRelease(ModelType.GAZ_66_1964.getYearRelease())
                    .build());
            models.add(Model.builder()
                    .name(ModelType.URAL_4320_1977.getName())
                    .yearRelease(ModelType.URAL_4320_1977.getYearRelease())
                    .build());
            models.add(Model.builder()
                    .name(ModelType.KAMAZ_65201_2000.getName())
                    .yearRelease(ModelType.KAMAZ_65201_2000.getYearRelease())
                    .build());

            repository.saveAllAndFlush(models);
        };
    }

    @Bean
    @Order(3)
    public CommandLineRunner enginesCommandLineRunner(EngineRepository repository) {
        return args ->
                repository.saveAllAndFlush(List.of(
                        Engine.builder().name(EngineType.DIESEL.getName()).build(),
                        Engine.builder().name(EngineType.PETROL.getName()).build(),
                        Engine.builder().name(EngineType.TURBOCHARGED_DIESEL.getName()).build()
                ));
    }

    @Bean
    @Order(4)
    public CommandLineRunner countriesCommandLineRunner(CountryRepository repository) {
        return args -> repository.saveAndFlush(Country.builder().name(CountryType.RUSSIA.getName()).build());
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
                    countryRepository, photoRepository);
            analogLoaderAndLinkToCarPart(analogRepository, carPartRepository);
            log.info("*** CarPart STARTED ***");
        };
    }

    private void analogLoaderAndLinkToCarPart(AnalogRepository analogRepository, CarPartRepository carPartRepository) {
        List<Analog> analogs = new ArrayList<>(4);

        Optional<CarPart> carPartGZ_511 = carPartRepository.findByVendorCode(CarPartVendorCodeType.GZ_511_1601130_280.getVendorCode());
        carPartGZ_511.ifPresent(carPart -> analogs.add(Analog.builder()
                .vendor("No name (China)")
                .carPart(carPart)
                .build()));

        Optional<CarPart> carPartKmz740 = carPartRepository.findByVendorCode(CarPartVendorCodeType.KMZ_740_60_1008025_20.getVendorCode());
        carPartKmz740.ifPresent(carPart -> analogs.add(Analog.builder()
                .vendor(KAMAZ_RUSSIA)
                .carPart(carPart)
                .build()));

        Optional<CarPart> carPartKmz3937478 = carPartRepository.findByVendorCode(CarPartVendorCodeType.KMZ_3937478_2.getVendorCode());
        carPartKmz3937478.ifPresent(carPart ->
                analogs.add(Analog.builder()
                        .vendor("Cummins (China)")
                        .carPart(carPart)
                        .build())
        );

        Optional<CarPart> carPartUrl4320 = carPartRepository.findByVendorCode(CarPartVendorCodeType.URL_4320_02.getVendorCode());
        carPartUrl4320.ifPresent(carPart ->
                analogs.add(Analog.builder()
                        .vendor("URAL (Russia)")
                        .carPart(carPart)
                        .build())
        );

        List<Analog> analogsSaved = analogRepository.saveAllAndFlush(analogs);

        List<CarPart> carParts = new ArrayList<>();
        carParts.add(addAnalogsToCarPart(carPartGZ_511, List.of(analogsSaved.get(0))));
        carParts.add(addAnalogsToCarPart(carPartKmz740, List.of(analogsSaved.get(1), analogsSaved.get(2))));
        carParts.add(addAnalogsToCarPart(carPartUrl4320, List.of(analogsSaved.get(3))));

        carPartRepository.saveAllAndFlush(carParts);
    }

    private CarPart addAnalogsToCarPart(Optional<CarPart> carPartOptional, List<Analog> analogs) {
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
                               PhotoRepository photoRepository) {

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
        Photo photoTwo_One = photos.get(3);
        Photo photoTwo_Two = photos.get(4);
        Photo photoThree_One = photos.get(5);
        Photo photoFour_One = photos.get(6);
        Photo photoSix_One = photos.get(7);

        // ID = 1
        CarPart cpGZ_750Z370_S = CarPart.builder()
                .brand(brandGaz)
                .model(modelGaz66_1964)
                .engine(engineDiesel)
                .country(countryRussia)
                .vendorCode(CarPartVendorCodeType.GZ_750Z370_S.getVendorCode())
                .sku("SKU-201902-0057")
                .name("ДИСК СЦЕПЛЕНИЯ")
                .description("Диск сцепления передает крутящий момент от ДВС к трансмиссии, выступает в качестве составляющей трения.")
                .price(59_720.66d)
                .manufacturer("ZF SACHS")
                .rating(9.7d)
                .photoList(List.of(photoOne_One, photoOne_Two, photoOne_Three))
                .build();

        // ID = 2
        CarPart cpURL_4320_01 = CarPart.builder()
                .brand(brandUral)
                .model(modelUral4320_1977)
                .engine(engineDiesel)
                .country(countryRussia)
                .vendorCode(CarPartVendorCodeType.URL_4320_01.getVendorCode())
                .sku("SKU-202212-01/20")
                .name("КОТЕЛ ПОДОГРЕВАТЕЛЯ")
                .description("Котел подогревателя предназначен для нагрева жидкости в системе охлаждения и масла в картере двигателя перед его пуском в холодный период времени.")
                .price(28_390.99d)
                .manufacturer("Ural")
                .rating(9.0d)
                .photoList(List.of(photoTwo_One, photoTwo_Two))
                .build();

        // ID = 3
        CarPart cpURL_4320_02 = CarPart.builder()
                .brand(brandUral)
                .model(modelUral4320_1977)
                .engine(engineDiesel)
                .country(countryRussia)
                .vendorCode(CarPartVendorCodeType.URL_4320_02.getVendorCode())
                .sku("SKU-202212-3703010")
                .name("Аккумулятор")
                .description("Аккумулятор – химический источник тока, выполняющий функции благодаря которым производится старт и движение машины.")
                .price(14_187d)
                .manufacturer("Ural")
                .rating(10.0d)
                .photoList(List.of(photoThree_One))
                .build();

        // ID = 4
        CarPart cpKMZ_740_51_1117010_0 = CarPart.builder()
                .brand(brandKamaz)
                .model(modelKamaz65301_2000)
                .engine(engineTurboDiesel)
                .country(countryRussia)
                .vendorCode(CarPartVendorCodeType.KMZ_740_51_1117010_01.getVendorCode())
                .sku("SKU-202212-1117010")
                .name("ТОПЛИВНЫЙ ТОНКОЙ ОЧИСТКИ ЕВРО С ПОДОГРЕВОМ")
                .description("Фильтр топливный тонкой очистки ЕВРО с подогревом купить на автомобиль КАМАЗ оптом и в розницу с доставкой по России. Сертификаты соответствия, схемы и инструкции по установке детали.")
                .price(10_760d)
                .manufacturer("LAZZ")
                .rating(10.0d)
                .photoList(List.of(photoFour_One))
                .build();

        // ID = 5
        CarPart cpKMZ_7403_1008021_02 = CarPart.builder()
                .brand(brandKamaz)
                .model(modelKamaz65301_2000)
                .engine(engineTurboDiesel)
                .country(countryRussia)
                .vendorCode(CarPartVendorCodeType.KMZ_7403_1008021_02.getVendorCode())
                .sku("SKU-202212-1008021")
                .name("КОЛЛЕКТОР ВЫПУСКНОЙ ЕВРО ЛЕВЫЙ")
                .description("Коллектор выпускной ЕВРО левый купить на автомобиль КАМАЗ оптом и в розницу с доставкой по России. Сертификаты соответствия, схемы и инструкции по установке детали.")
                .price(8_515d)
                .manufacturer("POO KAMAZ")
                .rating(5.0d)
                .photoList(List.of())
                .build();

        // ID = 6
        CarPart cpKMZ_7406_1012010_03 = CarPart.builder()
                .brand(brandKamaz)
                .model(modelKamaz65301_2000)
                .engine(engineTurboDiesel)
                .country(countryRussia)
                .vendorCode(CarPartVendorCodeType.KMZ_7406_1012010_03.getVendorCode())
                .sku("SKU-202212-1012010")
                .name("ФИЛЬТР МАСЛЯНЫЙ ГРУБОЙ ОЧИСТКИ ЕВРО ЧАСТИЧНОПОТОЧНЫЙ")
                .description("Фильтр масляный грубой очистки ЕВРО частичнопоточный купить на автомобиль КАМАЗ оптом и в розницу с доставкой по России. Сертификаты соответствия, схемы и инструкции по установке детали.")
                .price(1_545d)
                .manufacturer("LAAZ")
                .rating(7.7d)
                .photoList(List.of(photoSix_One))
                .build();

        // ID = 7
        CarPart cpGZ_511_1601130_280 = CarPart.builder()
                .brand(brandGaz)
                .model(modelGaz66_1964)
                .engine(enginePetrol)
                .country(countryRussia)
                .vendorCode(CarPartVendorCodeType.GZ_511_1601130_280.getVendorCode())
                .sku("SKU-20221228-0948-01")
                .name("ДИСК СЦЕПЛЕНИЯ ПОД ЛЕПЕСТКОВУЮ КОРЗИНУ ГАЗ-53,3307,66,3308,ПАЗ")
                .description("диск сцепления под лепестковую корзину ГАЗ-53,3307,66,3308,ПАЗ в упак.(змз).")
                .price(5_320d)
                .manufacturer("No name")
                .rating(3.7d)
                .photoList(List.of())
                .build();

        // ID = 8
        CarPart cpKMZ_740_60_1008025_20 = CarPart.builder()
                .brand(brandKamaz)
                .model(modelKamaz65301_2000)
                .engine(engineTurboDiesel)
                .country(countryRussia)
                .vendorCode(CarPartVendorCodeType.KMZ_740_60_1008025_20.getVendorCode())
                .sku("SKU-6520-6020-43-01")
                .name("Коллектор камаз-евро-3,4,5 выпускной")
                .description("Коллектор выпускной КАМАЗ левый. ДВС- КАМАЗ 740- Евро 3,4,5. В наличии. Возможно отправка ч/з ТК.")
                .price(5_000d)
                .manufacturer(KAMAZ_RUSSIA)
                .rating(5.5d)
                .photoList(List.of())
                .build();

        // ID = 9
        CarPart cpKMZ_3937478_2 = CarPart.builder()
                .brand(brandKamaz)
                .model(modelKamaz65301_2000)
                .engine(engineTurboDiesel)
                .country(countryRussia)
                .vendorCode(CarPartVendorCodeType.KMZ_3937478_2.getVendorCode())
                .sku("SKU-3937478-01")
                .name("Коллектор выпускной 1-2 цилиндров ISLe 3937478")
                .description("Новый оригинальный! Коллектор выпускной малый (1-2 цилиндра) 3937478, 3943850, 39377477, 3968361 на двигатель Cummins ISLe, L345, ISCE, QSC8.3, QSL9.")
                .price(23_000d)
                .manufacturer(KAMAZ_RUSSIA)
                .rating(10.0d)
                .photoList(List.of())
                .build();

        // ID = 10
        CarPart cpURL_4320_020 = CarPart.builder()
                .brand(brandUral)
                .model(modelUral4320_1977)
                .engine(engineDiesel)
                .country(countryRussia)
                .vendorCode(CarPartVendorCodeType.URL_4320_020.getVendorCode())
                .sku("SKU-20221228-1032-01")
                .name("Аккумулятор 190 АЧ")
                .description("Аккумулятор 190 ач на Камаз. Цена указана с учетом сдачи старого АКБ. Без обмена цена 9500р.")
                .price(7_500d)
                .manufacturer("Ural")
                .rating(10.0d)
                .photoList(List.of())
                .build();

        carPartRepository.saveAllAndFlush(List.of(
                cpGZ_750Z370_S,
                cpURL_4320_01,
                cpURL_4320_02,
                cpKMZ_740_51_1117010_0,
                cpKMZ_7403_1008021_02,
                cpKMZ_7406_1012010_03,
                cpGZ_511_1601130_280,
                cpKMZ_740_60_1008025_20,
                cpKMZ_3937478_2,
                cpURL_4320_020
        ));

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
