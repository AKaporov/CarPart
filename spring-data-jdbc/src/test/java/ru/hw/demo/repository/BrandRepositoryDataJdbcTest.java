//package ru.hw.demo.repository;
//
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.test.context.TestPropertySource;
//import ru.hw.demo.domain.Brand;
//
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@DataJpaTest
//@TestPropertySource(properties = {"spring.datasource.data=brand-test.sql"})
//@DisplayName("Репозиторий по работе с Маркой автомобиля")
//class BrandRepositoryDataJdbcTest {
//    private static final long URAL_ID = 2L;
//    private static final String URAL_NAME = "Ural";
//    private static final String EXPECTED_NAME = "GAZ";
//
//    @Autowired
//    private BrandRepositoryDataJdbc repository;
//
//    @AfterEach
//    void tearDown() {
//        repository.deleteAll();
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
//}