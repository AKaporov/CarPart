package ru.hw.demo.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import ru.hw.demo.domain.Brand;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@JdbcTest
@Import(value = {BrandRepositoryDataJdbcImpl.class})
@TestPropertySource(properties = {"spring.datasource.data=brand-test.sql"})
@DisplayName("Репозиторий на основе Spring Data JDBC по работе с Маркой автомобиля")
class BrandRepositoryDataJdbcImplTest {
    private static final long URAL_ID = 2L;
    private static final String URAL_NAME = "Ural";
    private static final String EXPECTED_NAME = "GAZ";
    private static final String NOT_VALID_NAME = "Oka";
    public static final String VOLGA_NAME = "Volga";
    public static final long VOLGA_ID = 3L;
    public static final String KAMAZ_NAME = "Kamaz";
    public static final long KAMAZ_ID = 1L;

    @Autowired
    private BrandRepositoryDataJdbcImpl repository;

    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }

    @Test
    @DisplayName("должен корректно сохранять новую марку автомобиля")
    void shouldSave() {
        Brand brand = Brand.builder()
                .name(EXPECTED_NAME)
                .build();

        Brand actualBrand = repository.insert(brand);

        assertAll(() -> {
            assertNotNull(actualBrand);
            assertNotNull(actualBrand.getId());
            assertEquals(EXPECTED_NAME, actualBrand.getName());
        });
    }

    @Test
    @DisplayName("должен находить марку по её идентификатору")
    void shouldFindById() {
        Optional<Brand> actualBrand = repository.findById(URAL_ID);

        Brand expectedBrand = Brand.builder()
                .id(URAL_ID)
                .name(URAL_NAME)
                .build();
        assertEquals(Optional.of(expectedBrand), actualBrand);
    }

    @Test
    @DisplayName("должен найти марку по её корректному наименованию")
    void shouldFindBrandByCorrectName() {
        Optional<Brand> actualBrand = repository.findByName(URAL_NAME);

        Brand expectedBrand = Brand.builder()
                .id(URAL_ID)
                .name(URAL_NAME)
                .build();

        assertThat(actualBrand).isPresent()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(expectedBrand);
    }

    @Test
    @DisplayName("не должен находить марку по не корректному наименованию")
    void shouldNotFoundBrandByNotValidName() {
        Optional<Brand> actualBrand = repository.findByName(NOT_VALID_NAME);

        assertThat(actualBrand).isNotPresent();
    }

    @Test
    @DisplayName("должен удалить все марки")
    void shouldDeleteAllBrands() {
        Optional<Brand> beforeBrand = repository.findById(URAL_ID);
        assertThat(beforeBrand).isPresent();

        repository.deleteAll();

        Optional<Brand> afterBrand = repository.findById(URAL_ID);
        assertThat(afterBrand).isNotPresent();

    }

    @Test
    @DisplayName("должен найти все марки")
    void shouldFindAllBrands() {
        List<Brand> actualBrands = repository.findAll();

        Brand kamazBrand = Brand.builder().id(KAMAZ_ID).name(KAMAZ_NAME).build();
        Brand uralBrand = Brand.builder().id(URAL_ID).name(URAL_NAME).build();
        Brand volgaBrand = Brand.builder().id(VOLGA_ID).name(VOLGA_NAME).build();
        List<Brand> expectedBrands = List.of(volgaBrand, kamazBrand, uralBrand);

        assertThat(actualBrands).isNotEmpty().hasSize(expectedBrands.size())
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyInAnyOrderElementsOf(expectedBrands);
    }

    @Test
    @DisplayName("должен найти все марки по дополнительному условию в секции where")
    void shouldFindAllByWhere() {
        Brand uralBrand = Brand.builder()
                .id(URAL_ID)
                .name(URAL_NAME)
                .build();
        Brand volgaBrand = Brand.builder()
                .id(VOLGA_ID)
                .name(VOLGA_NAME)
                .build();
        Set<String> names = Set.of(uralBrand.getName(), volgaBrand.getName());

        List<Brand> actualBrandList = repository.findAllByNames(names);

        List<Brand> expectedBrandList = List.of(volgaBrand, uralBrand);
        assertThat(actualBrandList).isNotEmpty().hasSize(expectedBrandList.size())
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyInAnyOrderElementsOf(expectedBrandList);
    }
}