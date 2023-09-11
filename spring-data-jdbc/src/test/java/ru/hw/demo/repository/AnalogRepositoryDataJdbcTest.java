package ru.hw.demo.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import ru.hw.demo.domain.*;
import ru.hw.demo.generate.CarPartGenerate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@DataJdbcTest
@TestPropertySource(properties = {"spring.sql.init.data-locations=analog-test.sql"})
@DisplayName("Репозиторий на основе Spring Data JDBC по работе с Аналогами запчастей")
class AnalogRepositoryDataJdbcTest {
    private static final Long ANALOG_ID_VALID = 2L;
    private static final Integer EXPECTED_ANALOG_SIZE = 2;
    private static final Long ANALOG_ID_NOT_VALID = 777L;

    @Autowired
    private AnalogRepositoryDataJdbcImpl analogRepository;
    @Autowired
    private CarPartRepositoryDataJdbc carPartRepositoryDataJdbc;
    @Autowired
    private BrandRepositoryDataJdbc brandRepositoryDataJdbc;
    @Autowired
    private CountryRepositoryDataJdbc countryRepositoryDataJdbc;
    @Autowired
    private EngineRepositoryDataJdbc engineRepositoryDataJdbc;
    @Autowired
    private ModelRepositoryDataJdbc modelRepositoryDataJdbc;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
        carPartRepositoryDataJdbc.deleteAll();
        analogRepository.deleteAll();
    }

    @Test
    @DisplayName("должен корректно сохранять новый аналог запасной части")
    void shouldSave() {
        CarPart uaz446Saved = createUaz446();
        CarPart moskvich2141Saved = createMoskvich2141();

        List<Analog> analogList = new ArrayList<>(2);
        analogList.add(Analog.builder()
                .carPart(uaz446Saved)
                .vendor("Тамбовская область")
                .build());
        analogList.add(Analog.builder()
                .carPart(moskvich2141Saved)
                .vendor("Кировская область")
                .build());

        List<Analog> actualAnalogList = analogRepository.saveAll(analogList);

        assertAll(() -> {
            assertNotNull(actualAnalogList);
            actualAnalogList.forEach(a -> assertThat(a.getId()).isPositive());
            assertEquals(EXPECTED_ANALOG_SIZE, actualAnalogList.size());
        });
    }

//    @Test
//    @DisplayName("должен находить аналог по его идентификатору")
//    @Disabled  // русские буквы в expectedCarPart
//    void shouldFindById() {
//        Optional<Analog> actualAnalog = analogRepository.findById(ANALOG_ID_VALID);
//
//        Country expectedCountry = Country.builder()
//                .name("Russia")
//                .id(1L)
//                .build();
//        Engine expectedEngine = Engine.builder()
//                .id(1L)
//                .name("Diesel")
//                .build();
//        Model expectedModel = Model.builder()
//                .id(2L)
//                .name("Ural-4320")
//                .yearRelease(1977)
//                .build();
//        Brand expectedBrand = Brand.builder()
//                .id(2L)
//                .name("Ural")
//                .build();
//        CarPart expectedCarPart = CarPart.builder()
//                .photos(new HashSet<>(1))
//                .analogs(new HashSet<>(1))
//                .countryRef(expectedCountry)
//                .engineRef(expectedEngine)
//                .modelRef(expectedModel)
//                .price(28390.99d)
//                .rating(9.0d)
//                .manufacturer("Ural")
//                .idTest(2L)
//                .description("Котел подогревателя предназначен для нагрева жидкости в системе охлаждения и масла в картере двигателя перед его пуском в холодный период времени.")
//                .name("КОТЕЛ ПОДОГРЕВАТЕЛЯ")
//                .sku("SKU-202212-01/20")
//                .vendorCode("URL-4320-01")
//                .brand(expectedBrand)
//                .build();
//
//        Analog expectedAnalog = Analog.builder()
//                .carPart(expectedCarPart)
//                .vendor("KAMAZ (Russia)")
//                .id(ANALOG_ID_VALID)
//                .build();
//
//        assertThat(actualAnalog).isNotEmpty()
//                .get()
//                .usingRecursiveComparison()
//                .isEqualTo(expectedAnalog);
//    }

    @Test
    @DisplayName("не должен находить аналог по его идентификатору")
    void shouldNotFindById() {
        Optional<Analog> actualAnalog = analogRepository.findById(ANALOG_ID_NOT_VALID);

        assertThat(actualAnalog).isEmpty();
    }

    @Test
    @DisplayName("должен удалить все аналоги")
    void shouldDeleteAllAnalogs() {
        List<Analog> beforeAll = analogRepository.findAll();
        assertThat(beforeAll).isNotEmpty();

        analogRepository.deleteAll();

        List<Analog> afterAll = analogRepository.findAll();
        assertThat(afterAll).isEmpty();
    }

    private CarPart createMoskvich2141() {
        Brand brand = brandRepositoryDataJdbc.insert(Brand.builder().name("Moskvich").build());
        Model model = modelRepositoryDataJdbc.save(Model.builder().name("Moskvich 2141").yearRelease(2_000).build());
        Engine engine = engineRepositoryDataJdbc.save(Engine.builder().name("Petrol Turbocharged").build());
        Country country = countryRepositoryDataJdbc.save(Country.builder().name("Lithuania").build());

        CarPart moskvich2141 = CarPartGenerate.getMoskvich2141(null);

        moskvich2141.setBrandRef(() -> brand.getId());
        moskvich2141.setModelRef(model::getId);
        moskvich2141.setEngineRef(engine::getId);
        moskvich2141.setCountryRef(country::getId);

        return carPartRepositoryDataJdbc.save(moskvich2141);
    }

    private CarPart createUaz446() {
        Brand brand = brandRepositoryDataJdbc.insert(Brand.builder().name("UAZ").build());
        Model model = modelRepositoryDataJdbc.save(Model.builder().name("UAZ-469").yearRelease(1972).build());
        Engine engine = engineRepositoryDataJdbc.save(Engine.builder().name("Turbocharged petrol").build());
        Country country = countryRepositoryDataJdbc.save(Country.builder().name("Morocco").build());

        CarPart uaz446 = CarPartGenerate.getUaz446(null);

        uaz446.setBrandRef(() -> brand.getId());
        uaz446.setModelRef(model::getId);
        uaz446.setEngineRef(engine::getId);
        uaz446.setCountryRef(country::getId);

        return carPartRepositoryDataJdbc.save(uaz446);
    }
}