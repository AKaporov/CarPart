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
@TestPropertySource(properties = {"spring.datasource.data=analog-test.sql"})
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
    @DisplayName("должен удалить все аналоги и не используемую связанную информацию")
    void shouldDeleteAllAnalogs() {
        Optional<Analog> byId = analogRepository.findById(1L);

        // TODO: 07.07.2023 Проверить работу теста shouldDeleteAll(). Не должно остаться данных в связанных таблицах от Удаленного аналога?
        Optional<Analog> beforeAnalog = analogRepository.findById(ANALOG_ID_VALID);
        assertThat(beforeAnalog).isNotEmpty();

        Optional<Brand> beforeBrand = brandRepositoryDataJdbc.findById(beforeAnalog.get().getCarPart().getBrandRef().getId());
        assertThat(beforeBrand).isNotEmpty();
        Optional<Model> beforeModel = modelRepositoryDataJdbc.findById(beforeAnalog.get().getCarPart().getModelRef().getId());
        assertThat(beforeModel).isNotEmpty();
        Optional<Engine> beforeEngine = engineRepositoryDataJdbc.findById(beforeAnalog.get().getCarPart().getEngineRef().getId());
        assertThat(beforeEngine).isNotEmpty();
        Optional<Country> beforeCountry = countryRepositoryDataJdbc.findById(beforeAnalog.get().getCarPart().getCountryRef().getId());
        assertThat(beforeCountry).isNotEmpty();
        Optional<CarPart> beforeCarPart = carPartRepositoryDataJdbc.findById(beforeAnalog.get().getCarPart().getId());
        assertThat(beforeCarPart).isNotEmpty();

        analogRepository.deleteAll();

        Optional<Analog> afterAnalog = analogRepository.findById(ANALOG_ID_VALID);
        assertThat(afterAnalog).isEmpty();

        // Марка используется в другой запчасти, поэтому должна остаться
        Optional<Brand> afterBrand = brandRepositoryDataJdbc.findById(beforeAnalog.get().getCarPart().getBrandRef().getId());
        assertEquals(beforeBrand, afterBrand);
        // Марка используется в другой запчасти, поэтому должна остаться
        Optional<Model> afterModel = modelRepositoryDataJdbc.findById(beforeAnalog.get().getCarPart().getModelRef().getId());
        assertEquals(beforeModel, afterModel);
        // Двигатель не используется в других запчастях, поэтому должен удалиться
        Optional<Engine> afterEngine = engineRepositoryDataJdbc.findById(beforeAnalog.get().getCarPart().getEngineRef().getId());
        assertThat(afterEngine).isEmpty();
        // Марка используется в другой запчасти, поэтому должна остаться
        Optional<Country> afterCountry = countryRepositoryDataJdbc.findById(beforeAnalog.get().getCarPart().getCountryRef().getId());
        assertEquals(beforeCountry, afterCountry);

        Optional<CarPart> afterCarPart = carPartRepositoryDataJdbc.findById(beforeAnalog.get().getCarPart().getId());
        assertThat(afterCarPart).isEmpty();
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