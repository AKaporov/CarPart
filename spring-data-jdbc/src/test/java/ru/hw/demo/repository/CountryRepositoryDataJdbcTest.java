package ru.hw.demo.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import ru.hw.demo.domain.Country;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@DataJdbcTest
@TestPropertySource(properties = {"spring.datasource.data=country-test.sql"})
@DisplayName("Репозиторий на основе Data JDBC по работе с объектом Страна производства")
class CountryRepositoryDataJdbcTest {
    private static final Long BELARUS_ID = 3L;
    private static final String BELARUS_NAME = "Belarus";
    private static final String EXPECTED_NAME = "Morocco";

    @Autowired
    private CountryRepositoryDataJdbc repository;

    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }

    @Test
    @DisplayName("должен находить страну по переданному идентификатору")
    void shouldFindCountryByValidId() {
        Optional<Country> actualCountry = repository.findById(BELARUS_ID);

        Country expectedCountry = Country.builder()
                .id(BELARUS_ID)
                .name(BELARUS_NAME)
                .build();

        assertThat(actualCountry).isPresent()
                .get()
                .isEqualTo(expectedCountry);
    }

    @Test
    @DisplayName("не должен находить страну по переданному идентификатору")
    void shouldNotFoundCountryByNotValidId() {
        Optional<Country> actualCountry = repository.findById(101L);
        assertTrue(actualCountry.isEmpty());
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
}