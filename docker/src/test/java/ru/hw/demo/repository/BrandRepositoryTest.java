package ru.hw.demo.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import ru.hw.demo.config.TestContainersEnvironment;
import ru.hw.demo.domain.Brand;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@TestPropertySource(properties = {"spring.sql.init.data-locations=classpath:brand-test.sql"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DisplayName("Репозиторий по работе с Маркой автомобиля")
class BrandRepositoryTest extends TestContainersEnvironment {

    private static final long URAL_ID = 4L;
    private static final String URAL_NAME = "Ural-Ural";
    private static final String EXPECTED_NAME = "GAZ";

    @Autowired
    private BrandRepository repository;


//    @Container
//    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:13.2-alpine")
//            .withDatabaseName("CarPartDB_Docker_Test")
//            .withUsername("cp_usr")
//            .withPassword("cp_PostgreSQL");
////            .withInitScript("schema.sql");
//
//
//    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
//        public void initialize(ConfigurableApplicationContext applicationContext) {
//            TestPropertyValues.of(
//                    "spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
//                    "spring.datasource.username=" + postgreSQLContainer.getUsername(),
//                    "spring.datasource.password=" + postgreSQLContainer.getPassword()
//            ).applyTo(applicationContext.getEnvironment());
//        }
//    }

// работает без postgreSQLContainer.start(); потому что есть аннотация @Container
//    @BeforeAll
//    static void beforeAll() {
//        postgreSQLContainer.start();
//    }
//
//    @AfterAll
//    static void tearDown() {
////        repository.deleteAllInBatch();
//        postgreSQLContainer.stop();
//    }

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
            assertTrue(actualBrand.getId() > 0);
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