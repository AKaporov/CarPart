package ru.hw.demo.service.component;

import com.querydsl.core.types.Predicate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.hw.demo.domain.CarPart;
import ru.hw.demo.pojo.FilterCarPart;
import ru.hw.demo.repository.CarPartRepositoryDataJdbc;
import ru.hw.demo.repository.CustomCarPartRepositoryDataJdbc;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@ActiveProfiles("test")
@DisplayName("Компонент по созданию предиката для поиска запчасти по фильтру")
class CarPartSpecificationTest {

    @Autowired
    private CustomCarPartRepositoryDataJdbc customCarPartRepositoryDataJdbc;
    @Autowired
    private CarPartSpecification carPartSpecification;
    @Autowired
    private CarPartRepositoryDataJdbc carPartRepositoryDataJdbc;

    @Test
    @DisplayName("должен корректно преобразовывать переданный фильтр в предикат поиска и находить запчасть")
    void getCarPartByFullPredicate() {
        CarPart expectedOne = carPartRepositoryDataJdbc.findByVendorCode("GZ-750Z370-S").get();

        FilterCarPart filter = FilterCarPart.builder()
                .brandName(expectedOne.getBrandQueryDsl().getName())
                .modelName(expectedOne.getModelQueryDsl().getName())
                .yearRelease(expectedOne.getModelQueryDsl().getYearRelease())
                .engineName(expectedOne.getEngineQueryDsl().getName())
                .build();

        Predicate where = carPartSpecification.getPredicateByFilter(filter);

        List<CarPart> actualList = customCarPartRepositoryDataJdbc.findAll(where);

        List<CarPart> expectedList = new ArrayList<>(1);
        expectedList.add(expectedOne);

        assertThat(actualList).isNotNull().isNotEmpty().hasSize(expectedList.size())
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("photos", "analogs", "brandRef", "modelRef", "engineRef", "countryRef")
                .hasSameElementsAs(expectedList);
    }

    @Test
    @DisplayName("должен корректно находить запчасть по частичным параметрам поиска")
    void getCarPartByPredicate() {
        CarPart expectedCarPartOne = carPartRepositoryDataJdbc.findByVendorCode("GZ-750Z370-S").get();
        CarPart expectedCarPartTwo = carPartRepositoryDataJdbc.findByVendorCode("GZ-511.1601130-280").get();

        FilterCarPart filter = FilterCarPart.builder()
                .brandName(expectedCarPartTwo.getBrandQueryDsl().getName())
                .modelName("")
                .yearRelease(expectedCarPartTwo.getModelQueryDsl().getYearRelease())
                .build();

        Predicate where = carPartSpecification.getPredicateByFilter(filter);

        List<CarPart> actualList = customCarPartRepositoryDataJdbc.findAll(where);

        List<CarPart> expectedList = new ArrayList<>(2);
        expectedList.add(expectedCarPartOne);
        expectedList.add(expectedCarPartTwo);

        assertThat(actualList).isNotNull().isNotEmpty().hasSize(expectedList.size())
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("photos", "analogs", "brandRef", "modelRef", "engineRef", "countryRef")
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

        Predicate where = carPartSpecification.getPredicateByFilter(filter);

        List<CarPart> actualList = customCarPartRepositoryDataJdbc.findAll(where);

        assertThat(actualList).isNotNull().isEmpty();
    }
}