package ru.hw.demo.service.component;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import ru.hw.demo.config.TestContainersEnvironment;
import ru.hw.demo.domain.CarPart;
import ru.hw.demo.pojo.FilterCarPart;
import ru.hw.demo.repository.CarPartRepository;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("Компонент по созданию предиката для поиска запчасти по фильтру")
class CarPartSpecificationTest extends TestContainersEnvironment {

    @Autowired
    private CarPartSpecification carPartSpecification;
    @Autowired
    private CarPartRepository carPartRepository;

    @Transactional  // только для того, что бы достать информацию из Lazy-полей
    @Test
    @DisplayName("должен корректно преобразовывать переданный фильтр в предикат поиска и находить запчасть")
    void getCarPartByFullPredicate() {
        CarPart expected = carPartRepository.getOne(1L);

        FilterCarPart filter = FilterCarPart.builder()
                .brandName(expected.getBrand().getName())
                .modelName(expected.getModel().getName())
                .yearRelease(expected.getModel().getYearRelease())
                .engineName(expected.getEngine().getName())
                .build();

        Specification<CarPart> specification = carPartSpecification.getPredicateByFilter(filter);

        List<CarPart> actualList = carPartRepository.findAll(specification);

        assertThat(actualList).isNotNull().isNotEmpty().hasSize(List.of(expected).size())
                .usingElementComparatorIgnoringFields("brand", "model", "engine", "country", "photoList", "analogList")
                .hasSameElementsAs(List.of(expected));
    }

    @Transactional
    @Test
    @DisplayName("должен корректно находить запчасть по частичным параметрам поиска")
    void getCarPartByPredicate() {
        CarPart carPartOne = carPartRepository.findById(1L).get();
        CarPart carPartTwo = carPartRepository.findById(7L).get();

        FilterCarPart filter = FilterCarPart.builder()
                .brandName(carPartTwo.getBrand().getName())
                .modelName("")
                .yearRelease(carPartTwo.getModel().getYearRelease())
                .build();

        Specification<CarPart> specification = carPartSpecification.getPredicateByFilter(filter);

        List<CarPart> actualList = carPartRepository.findAll(specification);

        List<CarPart> expectedList = new ArrayList<>(2);
        expectedList.add(carPartOne);
        expectedList.add(carPartTwo);

        assertThat(actualList).isNotNull().isNotEmpty().hasSize(expectedList.size())
                .usingElementComparatorIgnoringFields("brand", "model", "engine", "country", "photoList", "analogList")
                .hasSameElementsAs(expectedList);
    }

    @Test
    @DisplayName("должен вернуть пустой список, если по параметрам фильтра ничего не найдено")
    void getEmptyResultIfFilterNotValid() {
        FilterCarPart filter = FilterCarPart.builder()
                .brandName("Ural")
                .modelName("Ural-4320")
                .yearRelease(2050)
                .build();

        Specification<CarPart> specification = carPartSpecification.getPredicateByFilter(filter);

        List<CarPart> actualList = carPartRepository.findAll(specification);

        assertThat(actualList).isNotNull().isEmpty();
    }
}