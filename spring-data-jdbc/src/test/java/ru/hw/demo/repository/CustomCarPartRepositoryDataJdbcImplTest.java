package ru.hw.demo.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.hw.demo.domain.CarPart;

import java.util.List;

import static com.querydsl.example.sql.QCarParts.carParts;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@DisplayName("Расширенный репозиторий на основе Spring Data JDBC по работе с объектом Запчасти автомобиля")
class CustomCarPartRepositoryDataJdbcImplTest {

    @Autowired
    private CarPartRepositoryDataJdbc carPartRepositoryDataJdbc;

    @Autowired
    private CustomCarPartRepositoryDataJdbc customCarPartRepositoryDataJdbc;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("должен находить все запчасти автомобиля по переданному Predicate")
    void shouldFinAllCarPartsByPredicate() {
        BooleanExpression eq = carParts.rating.goe(9.0d);

        List<CarPart> actualCarParts = customCarPartRepositoryDataJdbc.findAll(eq);

        CarPart cpOne = carPartRepositoryDataJdbc.findById(1L).get();
        CarPart cpTwo = carPartRepositoryDataJdbc.findById(2L).get();
        CarPart cpThree = carPartRepositoryDataJdbc.findById(3L).get();
        CarPart cpFour = carPartRepositoryDataJdbc.findById(4L).get();
        CarPart cpNine = carPartRepositoryDataJdbc.findById(9L).get();
        CarPart cpTen = carPartRepositoryDataJdbc.findById(10L).get();

        List<CarPart> expectedCarPars = List.of(cpOne, cpTwo, cpThree, cpFour, cpNine, cpTen);
        assertThat(actualCarParts).isNotEmpty().hasSize(expectedCarPars.size())
                .containsExactlyInAnyOrderElementsOf(expectedCarPars);
    }
}