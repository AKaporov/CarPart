package ru.hw.demo.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import ru.hw.demo.domain.Country;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestPropertySource(properties = {"spring.datasource.data=country-test.sql"})
@DisplayName("Репозиторий по работе с объектом Страна производства")
class CountryRepositoryTest {
    private static final Long CANADA_ID = 2L;
    private static final String CANADA_NAME = "Canada";
    private static final String EXPECTED_NAME = "Morocco";

    @Autowired
    private CountryRepository repository;

    @AfterEach
    void tearDown() {
        repository.deleteAllInBatch();
    }

    @Test
    @DisplayName("должен корректно сохранять новую страну производителя")
    void shouldSave() {
        Country country = Country.builder()
                .name(EXPECTED_NAME)
                .build();

        Country actualCountry = repository.save(country);

        assertAll(() -> {
            assertNotNull(actualCountry);
            assertNotNull(actualCountry.getId());
            assertEquals(EXPECTED_NAME, actualCountry.getName());
        });
    }

    @Test
    @DisplayName("должен находить страну по её идентификатору")
    void shouldFindById() {
        Optional<Country> actualCountry = repository.findById(CANADA_ID);

        Country expectedCountry = Country.builder()
                .id(CANADA_ID)
                .name(CANADA_NAME)
                .build();
        assertEquals(Optional.of(expectedCountry), actualCountry);
    }
}