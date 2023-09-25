package ru.hw.demo.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import ru.hw.demo.domain.*;
import ru.hw.demo.enums.EngineType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestPropertySource(properties = {"spring.sql.init.data-locations=analog-test.sql"})
@DisplayName("Репозиторий по работе с Аналогами запчастей")
class AnalogRepositoryTest {
    private static final Long ANALOG_ID = 2L;
    private static final Integer EXPECTED_ANALOG_SIZE = 2;

    @Autowired
    private AnalogRepository analogRepository;
    @Autowired
    private CarPartRepository carPartRepository;
    @Autowired
    private EngineRepository engineRepository;
    @Autowired
    private BrandRepository brandRepository;
    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private ModelRepository modelRepository;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
        carPartRepository.deleteAllInBatch();
        analogRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("должен корректно сохранять новый аналог запасной части")
    void shouldSave() {
        List<Engine> engines = engineRepository.findAll();
        Engine engineTurboDiesel = engines.stream()
                .filter(engine -> EngineType.TURBOCHARGED_DIESEL.getName().equals(engine.getName()))
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

        List<Analog> actualAnalogList = analogRepository.saveAll(analogList);

        assertAll(() -> {
            assertNotNull(actualAnalogList);
            actualAnalogList.forEach(a -> assertThat(a.getId()).isPositive());
            assertEquals(EXPECTED_ANALOG_SIZE, actualAnalogList.size());
        });
    }

    @Test
    @DisplayName("должен находить аналог по его идентификатору")
    void shouldFindById() {
        Optional<Analog> actualAnalog = analogRepository.findById(ANALOG_ID);

        Analog expectedAnalog = Analog.builder()
                .carPart(actualAnalog.get().getCarPart())
                .vendor("KAMAZ (Russia)")
                .build();

        assertAll(() -> {
            assertThat(actualAnalog).isNotEmpty()
                    .get()
                    .usingRecursiveComparison()
                    .ignoringFields("id")
                    .isEqualTo(expectedAnalog);
            assertThat(actualAnalog.get().getId()).isPositive();
        });
    }

    private Brand getBrandByName(String name) {
        return brandRepository.findByName(name)
                .orElseGet(() -> brandRepository.save(Brand.builder().name(name).build()));
    }

    private Country getCountryByName(String name) {
        return countryRepository.findByName(name)
                .orElseGet(() -> countryRepository.save(Country.builder().name(name).build()));
    }

    private Model getModelByNameAndYearRelease(String name, Integer yearRelease) {
        return modelRepository.findByNameAndYearRelease(name, yearRelease)
                .orElseGet(() -> modelRepository.save(Model.builder().name(name).yearRelease(yearRelease).build()));
    }
}