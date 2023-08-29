//package ru.hw.demo.service.component;

//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import ru.hw.demo.domain.CarPart;
//import ru.hw.demo.pojo.FilterCarPart;
//import ru.hw.demo.repository.CarPartRepositoryDataJdbc;
//
//import java.util.function.Predicate;

//@SpringBootTest
//@DisplayName("Компонент по созданию предиката для поиска запчасти по фильтру")
//class CarPartSpecificationTest {

//    @Autowired
//    private CarPartSpecification carPartSpecification;
//    @Autowired
//    private CarPartRepositoryDataJdbc carPartRepositoryDataJdbc;


//    @Transactional  // только для того, что бы достать информацию из Lazy-полей
//    @Test
//    @DisplayName("должен корректно преобразовывать переданный фильтр в предикат поиска и находить запчасть")
//    void getCarPartByFullPredicate() {
//        CarPart expected = carPartRepositoryDataJdbc.findById(1L).get();
//
//        FilterCarPart filter = FilterCarPart.builder()
//                .brandName(expected.getBrandRef().getName())
//                .modelName(expected.getModelRef().getName())
//                .yearRelease(expected.getModelRef().getYearRelease())
//                .engineName(expected.getEngineRef().getName())
//                .build();
//
//        Predicate<CarPart> specification = carPartSpecification.getPredicateByFilter(filter);
//
//        List<CarPart> actualList = carPartRepositoryDataJdbc.findAll(specification);
//
//        assertThat(actualList).isNotNull().isNotEmpty().hasSize(List.of(expected).size())
//                .usingElementComparatorIgnoringFields("brand", "model", "engine", "country", "photoList", "analogList")
//                .hasSameElementsAs(List.of(expected));
//    }
//
//    @Transactional
//    @Test
//    @DisplayName("должен корректно находить запчасть по частичным параметрам поиска")
//    void getCarPartByPredicate() {
//        CarPart carPartOne = carPartRepositoryDataJdbc.findById(1L).get();
//        CarPart carPartTwo = carPartRepositoryDataJdbc.findById(7L).get();
//
//        FilterCarPart filter = FilterCarPart.builder()
//                .brandName(carPartTwo.getBrandRef().getName())
//                .modelName("")
//                .yearRelease(carPartTwo.getModelRef().getYearRelease())
//                .build();
//
//        Predicate<CarPart> specification = carPartSpecification.getPredicateByFilter(filter);
//
//        List<CarPart> actualList = carPartRepositoryDataJdbc.findAll(specification);
//
//        List<CarPart> expectedList = new ArrayList<>(2);
//        expectedList.add(carPartOne);
//        expectedList.add(carPartTwo);
//
//        assertThat(actualList).isNotNull().isNotEmpty().hasSize(expectedList.size())
//                .usingElementComparatorIgnoringFields("brand", "model", "engine", "country", "photoList", "analogList")
//                .hasSameElementsAs(expectedList);
//    }
//
//    @Test
//    @DisplayName("должен вернуть пустой список, если по параметрам фильтра ничего не найдено")
//    void getEmptyResultIfFilterNotValid() {
//        FilterCarPart filter = FilterCarPart.builder()
//                .brandName("Ural")
//                .modelName("Ural-4320")
//                .yearRelease(2050)
//                .build();
//
//        Predicate<CarPart> specification = carPartSpecification.getPredicateByFilter(filter);
//
//        List<CarPart> actualList = carPartRepositoryDataJdbc.findAll(specification);
//
//        assertThat(actualList).isNotNull().isEmpty();
//    }
//}