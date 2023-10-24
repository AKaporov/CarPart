package ru.hw.demo.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import ru.hw.demo.config.TestContainersEnvironment;
import ru.hw.demo.domain.Model;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@TestPropertySource(properties = {"spring.sql.init.data-locations=classpath:model-test.sql"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DisplayName("Репозиторий по работе с Моделями автомобилей")
class ModelRepositoryTest extends TestContainersEnvironment {
    private static final Long URAL_4320_ID = 2L;
    private static final String EXPECTED_NAME = "UAZ-469";
    private static final String URAL_4320_NAME = "Ural-4320";
    private static final Integer URAL_4320_YEAR_RELEASE = 1977;

    @Autowired
    private ModelRepository repository;

    @AfterEach
    void tearDown() {
        repository.deleteAllInBatch();
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
                .name(URAL_4320_NAME)
                .yearRelease(URAL_4320_YEAR_RELEASE)
                .build();
        assertEquals(Optional.of(expectedModel), actualModel);
    }

    @Test
    @DisplayName("должен находить модель по её наименования и году выпуска")
    void shouldFinModelByNameAndYearRelease() {
        Optional<Model> actualModel = repository.findByNameAndYearRelease(URAL_4320_NAME, URAL_4320_YEAR_RELEASE);

        Model expectedModel = Model.builder()
                .id(URAL_4320_ID)
                .name(URAL_4320_NAME)
                .yearRelease(URAL_4320_YEAR_RELEASE)
                .build();
        assertEquals(Optional.of(expectedModel), actualModel);
    }
}