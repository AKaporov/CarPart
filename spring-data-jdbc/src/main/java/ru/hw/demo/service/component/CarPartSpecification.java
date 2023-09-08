package ru.hw.demo.service.component;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.stereotype.Component;
import ru.hw.demo.pojo.FilterCarPart;

import java.util.Objects;

import static com.querydsl.example.sql.QBrands.brands;
import static com.querydsl.example.sql.QEngines.engines;
import static com.querydsl.example.sql.QModels.models;

/**
 * @author Artem
 * Класс для создания предиката для поиска запчасти по фильтру
 */

@Component
public class CarPartSpecification {

    // PreparedStatement

//    @Autowired
//    private BrandRepositoryDataJdbc brandRepositoryDataJdbc;
//    @Autowired
//    private ModelRepositoryDataJdbc modelRepositoryDataJdbc;
//    @Autowired
//    private EngineRepositoryDataJdbc engineRepositoryDataJdbc;

//    @Autowired
//    private SimpleJdbcRepository jdbcRepository;

    public Predicate getPredicateByFilter(FilterCarPart filterCarPart) {
        BooleanExpression where = null;

        if (Objects.nonNull(filterCarPart.getBrandName()) && !filterCarPart.getBrandName().isEmpty()) {
            where = brands.name.eq(filterCarPart.getBrandName());
        }

        boolean isModuleName = Objects.nonNull(filterCarPart.getModelName()) && !filterCarPart.getModelName().isEmpty();
        if (isModuleName) {
            if (Objects.nonNull(where)) {
                where = where.and(models.name.eq(filterCarPart.getModelName()));
            } else {
                where = models.name.eq(filterCarPart.getModelName());
            }
        }

        boolean isModuleYear = Objects.nonNull(filterCarPart.getYearRelease()) && filterCarPart.getYearRelease() != 0;
        if (isModuleYear) {
            if (Objects.nonNull(where)) {
                where = where.and(models.yearRelease.eq(filterCarPart.getYearRelease()));
            } else {
                where = models.yearRelease.eq(filterCarPart.getYearRelease());
            }
        }

        if (Objects.nonNull(filterCarPart.getEngineName()) && !filterCarPart.getEngineName().isEmpty()) {
            if (Objects.nonNull(where)) {
                where = where.and(engines.name.eq(filterCarPart.getEngineName()));
            } else {
                where = engines.name.eq(filterCarPart.getEngineName());
            }
        }

        return where;

//        Predicate<CarPart> p = obj -> filterCarPart.getEngineName().equalsIgnoreCase(obj.getName());
//
//
//        Example<Engine> exampleEngine = Example.of(Engine.builder()
//                .name(filterCarPart.getEngineName())
//                .build());
////        Iterable<Engine> f = jdbcRepository.findAll(exampleEngine);
////        System.out.println("*** Kaporov jdbcRepository = " + f);
//
//        Iterable<Engine> all = engineRepositoryDataJdbc.findAll(exampleEngine);
//        System.out.printf("*** Kaporov engineRepositoryDataJdbc = ", all);
//
//        return p;

//        return (root, query, criteriaBuilder) -> {
//            List<Predicate> predicateList = new ArrayList<>(3);
//
//            getBrandPredicate(root.get("brand"), filterCarPart)
//                    .ifPresent(brand -> predicateList.add(brand));
//
//            getModelPredicate(root.get("model"), filterCarPart)
//                    .ifPresent(model -> predicateList.add(model));
//
//            getEnginePredicate(root.get("engine"), filterCarPart)
//                    .ifPresent(engine -> predicateList.add(engine));
//
//            return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));
//        };
    }

//    private Optional<Predicate> getEnginePredicate(Path<Object> engine, FilterCarPart filterCarPart) {
//        if (Objects.nonNull(filterCarPart.getEngineName()) && !filterCarPart.getEngineName().isEmpty()) {
////            Example<Engine> exampleEngine = Example.of(Engine.builder()
////                    .name(filterCarPart.getEngineName())
////                    .build());
////
////            List<Engine> engineList = engineRepositoryDataJdbc.findAll(exampleEngine);
//
//
//            List<Engine> engineList = engineRepositoryDataJdbc.findAllByName(filterCarPart.getEngineName());
//            if (engineList.isEmpty()) {
//                return Optional.of(engine.isNull());
//            } else {
//                return Optional.ofNullable(engine.in(engineList));
//            }
//        }
//
//        return Optional.empty();
//    }
//
//    private Optional<Predicate> getModelPredicate(Path<Object> model, FilterCarPart filterCarPart) {
//        boolean isModuleName = Objects.nonNull(filterCarPart.getModelName()) && !filterCarPart.getModelName().isEmpty();
//        boolean isModuleYear = Objects.nonNull(filterCarPart.getYearRelease()) && filterCarPart.getYearRelease() != 0;
//
//        if (isModuleName || isModuleYear) {
////            Example<Model> exampleModel = Example.of(Model.builder()
////                    .name(isModuleName ? filterCarPart.getModelName() : null)
////                    .yearRelease(isModuleYear ? filterCarPart.getYearRelease() : null)
////                    .build());
//
////            List<Model> modelList = modelRepositoryDataJdbc.findAll(exampleModel);
//
//            String name = isModuleName ? filterCarPart.getModelName() : null;
//            Integer yearRelease = isModuleYear ? filterCarPart.getYearRelease() : null;
//
//            List<Model> modelList = modelRepositoryDataJdbc.findAllByNameAndYearRelease(name, yearRelease);
//            if (modelList.isEmpty()) {
//                return Optional.of(model.isNull());
//            } else {
//                return Optional.ofNullable(model.in(modelList));
//            }
//        }
//
//        return Optional.empty();
//    }
//
//    private Optional<Predicate> getBrandPredicate(Path<Object> brand, FilterCarPart filterCarPart) {
//        if (Objects.nonNull(filterCarPart.getBrandName()) && !filterCarPart.getBrandName().isEmpty()) {
////            Example<Brand> exampleBrand = Example.of(Brand.builder()
////                    .name(filterCarPart.getBrandName())
////                    .build());
//
////            List<Brand> brandLst = brandRepositoryDataJdbc.findAll(exampleBrand);
//            Set<String> brandName = Set.of(filterCarPart.getBrandName());
//            List<Brand> brandLst = brandRepositoryDataJdbc.findAllByNames(brandName);
//            if (brandLst.isEmpty()) {
//                return Optional.of(brand.isNull());
//            } else {
//                return Optional.ofNullable(brand.in(brandLst));
//            }
//        }
//
//        return Optional.empty();
//    }
}
