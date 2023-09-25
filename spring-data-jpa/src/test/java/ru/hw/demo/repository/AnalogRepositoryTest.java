package ru.hw.demo.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import ru.hw.demo.domain.*;
import ru.hw.demo.generate.CarPartGenerate;

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
        CarPart uaz446 = CarPartGenerate.getUaz446(null);
        CarPart uaz446Saved = carPartRepository.save(uaz446);
        CarPart moskvich2141 = CarPartGenerate.getMoskvich2141(null);
        CarPart moskvich2141Saved = carPartRepository.save(moskvich2141);

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
}