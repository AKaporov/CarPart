package ru.hw.demo.repository;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import ru.hw.demo.domain.*;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@JdbcTest
@Import({AnalogRepositoryDataJdbcImpl.class})
@TestPropertySource(properties = {"spring.datasource.data=analog-test.sql"})
@DisplayName("Репозиторий на основе Spring Data JDBC по работе с Аналогами запчастей")
class AnalogRepositoryDataJdbcTest {
    private static final Long ANALOG_ID_VALID = 2L;
    private static final Integer EXPECTED_ANALOG_SIZE = 2;
    private static final Long ANALOG_ID_NOT_VALID = 777L;

    @Autowired
    private AnalogRepositoryDataJdbcImpl analogRepository;
//    @Autowired
//    private CarPartRepositoryDataJdbc carPartRepository;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
//        carPartRepository.deleteAll();
        analogRepository.deleteAll();
    }

//    @Test
//    @DisplayName("должен корректно сохранять новый аналог запасной части")
//    void shouldSave() {
//        CarPart uaz446 = CarPartGenerate.getUaz446(null);
//        CarPart uaz446Saved = carPartRepository.save(uaz446);
//        CarPart moskvich2141 = CarPartGenerate.getMoskvich2141(null);
//        CarPart moskvich2141Saved = carPartRepository.save(moskvich2141);
//
//        List<Analog> analogList = new ArrayList<>(2);
//        analogList.add(Analog.builder()
//                .carPart(uaz446Saved)
//                .vendor("Тамбовская область")
//                .build());
//        analogList.add(Analog.builder()
//                .carPart(moskvich2141Saved)
//                .vendor("Кировская область")
//                .build());
//
//        List<Analog> actualAnalogList = analogRepository.saveAll(analogList);
//
//        assertAll(() -> {
//            assertNotNull(actualAnalogList);
//            actualAnalogList.forEach(a -> assertThat(a.getId()).isPositive());
//            assertEquals(EXPECTED_ANALOG_SIZE, actualAnalogList.size());
//        });
//    }

    @Test
    @DisplayName("должен находить аналог по его идентификатору")
    @Disabled  // русские буквы в expectedCarPart
    void shouldFindById() {
        Optional<Analog> actualAnalog = analogRepository.findById(ANALOG_ID_VALID);

        Country expectedCountry = Country.builder()
                .name("Russia")
                .id(1L)
                .build();
        Engine expectedEngine = Engine.builder()
                .id(1L)
                .name("Diesel")
                .build();
        Model expectedModel = Model.builder()
                .id(2L)
                .name("Ural-4320")
                .yearRelease(1977)
                .build();
        Brand expectedBrand = Brand.builder()
                .id(2L)
                .name("Ural")
                .build();
        CarPart expectedCarPart = CarPart.builder()
                .photoList(new ArrayList<>(1))
                .analogList(new ArrayList<>(1))
                .country(expectedCountry)
                .engine(expectedEngine)
                .model(expectedModel)
                .price(28390.99d)
                .rating(9.0d)
                .manufacturer("Ural")
                .id(2L)
                .description("Котел подогревателя предназначен для нагрева жидкости в системе охлаждения и масла в картере двигателя перед его пуском в холодный период времени.")
                .name("КОТЕЛ ПОДОГРЕВАТЕЛЯ")
                .sku("SKU-202212-01/20")
                .vendorCode("URL-4320-01")
                .brand(expectedBrand)
                .build();

        Analog expectedAnalog = Analog.builder()
                .carPart(expectedCarPart)
                .vendor("KAMAZ (Russia)")
                .id(ANALOG_ID_VALID)
                .build();

        assertThat(actualAnalog).isNotEmpty()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(expectedAnalog);
    }

    @Test
    @DisplayName("не должен находить аналог по его идентификатору")
    void shouldNotFindById() {
        Optional<Analog> actualAnalog = analogRepository.findById(ANALOG_ID_NOT_VALID);

        assertThat(actualAnalog).isEmpty();
    }

    @Test
    @DisplayName("должен удалить все аналоги")
    void shouldDeleteAll() {
        // TODO: 07.07.2023 Проверить работу теста shouldDeleteAll(). Не должно остаться данных в связанных таблицах от Удаленного аналога?
        Optional<Analog> before = analogRepository.findById(ANALOG_ID_VALID);
        assertThat(before).isNotEmpty();


        analogRepository.deleteAll();

        Optional<Analog> after = analogRepository.findById(ANALOG_ID_VALID);
        assertThat(after).isEmpty();
    }
}