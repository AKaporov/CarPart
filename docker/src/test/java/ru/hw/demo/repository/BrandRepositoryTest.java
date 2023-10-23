package ru.hw.demo.repository;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.hw.demo.DockerApplication;
import ru.hw.demo.config.ContainersEnvironment;
import ru.hw.demo.containers.PostgresTestContainer;
import ru.hw.demo.domain.Brand;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

//@DataJpaTest
@ActiveProfiles("test")
//@TestPropertySource(properties = {"spring.sql.init.data-locations=classpath:brand-test.sql"})

//@SpringBootTest
//@ContextConfiguration(initializers = {BrandRepositoryTest.Initializer.class})
//@Testcontainers

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = DockerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

@DisplayName("Репозиторий по работе с Маркой автомобиля")
class BrandRepositoryTest extends ContainersEnvironment {

    private static final long URAL_ID = 2L;
    private static final String URAL_NAME = "Ural";
    private static final String EXPECTED_NAME = "GAZ";

    @Autowired
    private BrandRepository repository;


//    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:13.2-alpine")
//            .withDatabaseName("CarPartDB_Docker_Test")
//            .withUsername("cp_usr")
//            .withPassword("cp_PostgreSQL")
//            .withInitScript("brand-test.sql");
//
//    public class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
//        public void initialize(ConfigurableApplicationContext applicationContext) {
//            TestPropertyValues.of(
//                    "spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
//                    "spring.datasource.username=" + postgreSQLContainer.getUsername(),
//                    "spring.datasource.password=" + postgreSQLContainer.getPassword()
//            ).applyTo(applicationContext.getEnvironment());
//        }
//    }


//    @BeforeAll
//    static void beforeEach() {
//        postgreSQLContainer.start();
//    }

    @AfterEach
    void tearDown() {
        repository.deleteAllInBatch();
//        postgreSQLContainer.stop();
    }

    @Test
    @DisplayName("должен корректно сохранять новую марку автомобиля")
    void shouldSaveBrand() {
        Brand brand = Brand.builder()
                .name(EXPECTED_NAME)
                .build();

        Brand actualBrand = repository.save(brand);

        assertAll(() -> {
            assertNotNull(actualBrand);
            assertNotNull(actualBrand.getId());
            assertEquals(EXPECTED_NAME, actualBrand.getName());
        });
    }

    @Test
    @DisplayName("должен находить марку по её идентификатору")
    void shouldFindBrandById() {
        Optional<Brand> actualBrand = repository.findById(URAL_ID);

        Brand expectedBrand = Brand.builder()
                .id(URAL_ID)
                .name(URAL_NAME)
                .build();
        assertEquals(Optional.of(expectedBrand), actualBrand);
    }

    @Test
    @DisplayName("должен находить марку по её названию")
    void shouldFindBrandByName() {
        Optional<Brand> actualBrand = repository.findByName(URAL_NAME);

        Brand expectedBrand = Brand.builder()
                .id(URAL_ID)
                .name(URAL_NAME)
                .build();
        assertEquals(Optional.of(expectedBrand), actualBrand);
    }
}