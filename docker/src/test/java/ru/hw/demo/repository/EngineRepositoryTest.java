package ru.hw.demo.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import ru.hw.demo.config.TestContainersEnvironment;
import ru.hw.demo.domain.Engine;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@TestPropertySource(properties = {"spring.sql.init.data-locations=engine-test.sql"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DisplayName("Репозиторий по работе с Двигателем")
class EngineRepositoryTest extends TestContainersEnvironment {
    private static final Long PETROL_ID = 4L;
    private static final String PETROL_NAME = "Petrol-Petrol";
    private static final String EXPECTED_NAME = "Turbocharged petrol";

    @Autowired
    private EngineRepository repository;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
        repository.deleteAllInBatch();
    }

    @Test
    @DisplayName("должен корректно сохранять новый двигатель")
    void shouldSave() {
        Engine engine = Engine.builder()
                .name(EXPECTED_NAME)
                .build();

        Engine actualEngine = repository.save(engine);

        assertAll(() -> {
            assertNotNull(actualEngine);
            assertNotNull(actualEngine.getId());
            assertEquals(EXPECTED_NAME, actualEngine.getName());
        });
    }

    @Test
    @DisplayName("должен находить двигатель по его идентификатору")
    void shouldFindById() {
        Optional<Engine> actualEngine = repository.findById(PETROL_ID);

        Engine expectedEngine = Engine.builder()
                .id(PETROL_ID)
                .name(PETROL_NAME)
                .build();
        assertEquals(Optional.of(expectedEngine), actualEngine);
    }
}