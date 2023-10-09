package ru.hw.demo.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import ru.hw.demo.domain.Engine;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestPropertySource(properties = {"spring.sql.init.data-locations=engine-test.sql"})
@DisplayName("Репозиторий по работе с Двигателем")
class EngineRepositoryTest {
    private static final Long PETROL_ID = 2L;
    private static final String PETROL_NAME = "Petrol";
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