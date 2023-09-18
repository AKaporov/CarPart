package ru.hw.demo.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import ru.hw.demo.domain.Brand;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@TestPropertySource(properties = {"spring.sql.init.data-locations=classpath:brand-test.sql"})
@DisplayName("Репозиторий по работе с Маркой автомобиля")
class BrandRepositoryTest {

@Test
    void should() {

    }
//    private static final long URAL_ID = 2L;
//    private static final String URAL_NAME = "Ural";
//    private static final String EXPECTED_NAME = "GAZ";
//
//    @Autowired
//    private BrandRepository repository;
//
//    @AfterEach
//    void tearDown() {
//        repository.deleteAllInBatch();
//    }
//
//    @Test
//    @DisplayName("должен корректно сохранять новую марку автомобиля")
//    void shouldSave() {
//        Brand brand = Brand.builder()
//                .name(EXPECTED_NAME)
//                .build();
//
//        Brand actualBrand = repository.save(brand);
//
//        assertAll(() -> {
//            assertNotNull(actualBrand);
//            assertNotNull(actualBrand.getId());
//            assertEquals(EXPECTED_NAME, actualBrand.getName());
//        });
//    }
//
//    @Test
//    @DisplayName("должен находить марку по её идентификатору")
//    void shouldFindById() {
//        Optional<Brand> actualBrand = repository.findById(URAL_ID);
//
//        Brand expectedBrand = Brand.builder()
//                .id(URAL_ID)
//                .name(URAL_NAME)
//                .build();
//        assertEquals(Optional.of(expectedBrand), actualBrand);
//    }
}