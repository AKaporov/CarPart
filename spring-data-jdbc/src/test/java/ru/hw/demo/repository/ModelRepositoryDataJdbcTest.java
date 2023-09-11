package ru.hw.demo.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import ru.hw.demo.domain.Model;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@DataJdbcTest
@TestPropertySource(properties = {"spring.sql.init.data-locations=model-test.sql"})
@DisplayName("Репозиторий на основе Spring Data JDBC по работе с объектом Модель автомобилей")
class ModelRepositoryDataJdbcTest {
    private static final Long URAL_4320_ID = 2L;
    private static final String EXPECTED_NAME = "UAZ-469";

    @Autowired
    private ModelRepositoryDataJdbc repository;

    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }

    @Test
    @DisplayName("должен корректно сохранять новый модели")
    void shouldSave() {
        Model model = Model.builder()
                .name(EXPECTED_NAME)
                .yearRelease(1972)
                .build();

        Model actualModel = repository.save(model);

        assertAll(() -> {
            assertNotNull(actualModel);
            assertNotNull(actualModel.getId());
            assertEquals(EXPECTED_NAME, actualModel.getName());
        });
    }

    @Test
    @DisplayName("должен находить модель по его идентификатору")
    void shouldFindById() {
        Optional<Model> actualModel = repository.findById(URAL_4320_ID);

        Model expectedModel = Model.builder()
                .id(URAL_4320_ID)
                .name("Ural-4320")
                .yearRelease(1977)
                .build();
        assertEquals(Optional.of(expectedModel), actualModel);
    }

    @Test
    @DisplayName("не должен находить модель по переданному идентификатору")
    void shouldNotFoundModelByNotValidId() {
        Optional<Model> actualModel = repository.findById(101L);
        assertTrue(actualModel.isEmpty());
    }
}