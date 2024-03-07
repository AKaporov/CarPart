package ru.hw.demo.repository;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import ru.hw.demo.config.TestContainersEnvironment;
import ru.hw.demo.domain.*;
import ru.hw.demo.enums.CountryType;
import ru.hw.demo.enums.EngineType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@ActiveProfiles("test")
@TestPropertySource(properties = {"spring.sql.init.data-locations=carpart-test.sql"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DisplayName("Репозиторий по работе с Запчасти автомобиля")
class CarPartRepositoryTest extends TestContainersEnvironment {

    private static final int EXPECTED_QUERIES_COUNT = 2;
    @Autowired
    private TestEntityManager em;
    @Autowired
    private CarPartRepository carPartRepository;
    @Autowired
    private BrandRepository brandRepository;
    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private EngineRepository engineRepository;
    @Autowired
    private ModelRepository modelRepository;
    @Autowired
    private AnalogRepository analogRepository;
    @Autowired
    private PhotoRepository photoRepository;

    @AfterEach
    void tearDown() {
        carPartRepository.deleteAllInBatch();
        analogRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("должен сохранять новую запчасть")
    void shouldSaveCarPart() {
        List<Engine> engines = engineRepository.findAll();
        Engine engineTurboDiesel = engines.stream()
                .filter(engine -> EngineType.TURBOCHARGED_DIESEL.getName().equals(engine.getName()))
                .findFirst()
                .orElseGet(() -> Engine.builder().build());
        Engine enginePetrol = engines.stream()
                .filter(engine -> EngineType.PETROL.getName().equals(engine.getName()))
                .findFirst()
                .orElseGet(() -> Engine.builder().build());

        Brand brandUaz = getBrandByName("UAZ");
        Model modelUaz469 = getModelByNameAndYearRelease("UAZ-469", 1972);
        Country countryMorocco = getCountryByName("Morocco");

        CarPart uaz446 = CarPart.builder()
                .brand(brandUaz)
                .model(modelUaz469)
                .engine(engineTurboDiesel)
                .country(countryMorocco)
                .photoList(new ArrayList<>(1))
                .analogList(new ArrayList<>(1))
                .name("Bridge UAZ-469 Military Front")
                .description("Bridge UAZ-469 Military Front. Guarantee from 6 months.")
                .manufacturer("Ulyanovsk Motor Plant")
                .price(45_000)
                .sku("202212-2103238563")
                .vendorCode("UAZ-469-01")
                .rating(4.8)
                .build();
        CarPart uaz446Saved = carPartRepository.save(uaz446);

        Brand brandMoskvich = getBrandByName("Moskvich");
        Model modelMoskvich2141 = getModelByNameAndYearRelease("Moskvich 2141", 2000);
        Country countryLithuania = getCountryByName("Lithuania");
        CarPart moskvich2141 = CarPart.builder()
                .brand(brandMoskvich)
                .model(modelMoskvich2141)
                .engine(engineTurboDiesel)
                .country(countryLithuania)
                .photoList(null)
                .analogList(null)
                .name("Trunk lid for Moskvich 2141")
                .description("Auto parsing.")
                .manufacturer("\"SIGMA\", LITHUANIAN SOFTWARE")
                .price(3_000)
                .sku("202212-2610130591")
                .vendorCode("MSK-2141-01")
                .rating(0)
                .build();
        CarPart moskvich2141Saved = carPartRepository.save(moskvich2141);

        List<Analog> analogList = new ArrayList<>(2);
        analogList.add(analogRepository.save(Analog.builder()
                .carPart(uaz446Saved)
                .vendor("Тамбовская область")
                .build()));
        analogList.add(analogRepository.save(Analog.builder()
                .carPart(moskvich2141Saved)
                .vendor("Кировская область")
                .build()));

        List<Photo> photoList = new ArrayList<>(1);
        photoList.add(photoRepository.save(Photo.builder()
                .photoUrl("https://localhost:8080/carpart/zaz/1/#&gid=1&pid=1")
                .build()));

        Brand brandZaz = getBrandByName("ZAZ");
        Model modelZaz965_1960 = getModelByNameAndYearRelease("ZAZ-965", 1960);
        Country countryRussia = getCountryByName(CountryType.RUSSIA.getName());

        CarPart cp = CarPart.builder()
                .brand(brandZaz)
                .model(modelZaz965_1960)
                .country(countryRussia)
                .engine(enginePetrol)
                .analogList(analogList)
                .photoList(photoList)
                .rating(5.5)
                .sku("ZZ-966-6106008-01")
                .vendorCode("20221228-01")
                .price(2000)
                .manufacturer("Russia")
                .description("Состояние отличное! Снят с рабочей машины!!!")
                .name("Бак топливный заз 965")
                .build();

        CarPart actualCarPart = carPartRepository.save(cp);

        assertAll(() -> {
            assertNotNull(actualCarPart);
            assertThat(actualCarPart.getId()).isPositive();
            assertThat(actualCarPart.getPhotoList().size()).isPositive();
            assertThat(actualCarPart.getAnalogList().size()).isPositive();
        });
    }

    private Country getCountryByName(String name) {
        return countryRepository.findByName(name)
                .orElseGet(() -> countryRepository.save(Country.builder().name(name).build()));
    }

    private Model getModelByNameAndYearRelease(String name, Integer yearRelease) {
        return modelRepository.findByNameAndYearRelease(name, yearRelease)
                .orElseGet(() -> modelRepository.save(Model.builder().name(name).yearRelease(yearRelease).build()));
    }

    @Test
    @DisplayName("должен находить запчасть по её Идентификатору")
    void shouldFindCarPartById() {
        Optional<CarPart> actualCarPart = carPartRepository.findById(2L);
        assertAll(() -> {
            assertNotNull(actualCarPart);
            assertThat(actualCarPart).isNotEmpty();
            assertThat(actualCarPart.get().getId()).isPositive();
            assertThat(actualCarPart.get().getPhotoList()).isNotEmpty().hasSizeGreaterThan(0);
            assertThat(actualCarPart.get().getAnalogList()).isNotEmpty().hasSizeGreaterThan(0);
        });
    }

    @Test
    @DisplayName("должен находить запчасть по каталожному номеру и делать только \'" + EXPECTED_QUERIES_COUNT + "\' запроса к базе")
    void shouldFindCarPartByVendorCode() {
        Optional<CarPart> expected = carPartRepository.findById(3L);

        SessionFactory sessionFactory = em.getEntityManager().getEntityManagerFactory()
                .unwrap(SessionFactory.class);
        sessionFactory.getStatistics().setStatisticsEnabled(true);


        System.out.println("\n\n\n\n----------------------------------------------------------------------------------------------------------");
        Optional<CarPart> actual = carPartRepository.findByVendorCode("URL-4320-02");

        assertAll(() -> {
            assertThat(actual).isNotEmpty()
                    .get()
                    .usingRecursiveComparison()
                    .ignoringFields("photoList", "analogList")
                    .isEqualTo(expected.get());

            assertThat(actual.get().getPhotoList()).isNotEmpty()
                    .containsExactlyInAnyOrderElementsOf(expected.get().getPhotoList());
            assertThat(actual.get().getAnalogList()).isNotEmpty()
                    .containsExactlyInAnyOrderElementsOf(expected.get().getAnalogList());
        });
        System.out.println("----------------------------------------------------------------------------------------------------------\n\n\n\n");

        assertThat(sessionFactory.getStatistics().getPrepareStatementCount()).isEqualTo(EXPECTED_QUERIES_COUNT);
    }

    private Brand getBrandByName(String name) {
        return brandRepository.findByName(name)
                .orElseGet(() -> brandRepository.save(Brand.builder().name(name).build()));
    }
}