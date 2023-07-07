//package ru.hw.demo.service.component;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Example;
//import org.springframework.data.jpa.domain.Specification;
//import org.springframework.stereotype.Component;
//import ru.hw.demo.domain.Brand;
//import ru.hw.demo.domain.CarPart;
//import ru.hw.demo.domain.Engine;
//import ru.hw.demo.domain.Model;
//import ru.hw.demo.pojo.FilterCarPart;
//import ru.hw.demo.repository.BrandRepositoryDataJdbc;
//import ru.hw.demo.repository.EngineRepositoryDataJdbc;
//import ru.hw.demo.repository.ModelRepositoryDataJdbc;
//
//import javax.persistence.criteria.Path;
//import javax.persistence.criteria.Predicate;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Objects;
//import java.util.Optional;
//
///**
// * @author Artem
// * Класс для создания предиката для поиска запчасти по фильтру
// */
//
//@Component
//public class CarPartSpecification {
//
//    @Autowired
//    private BrandRepositoryDataJdbc brandRepositoryDataJdbc;
//    @Autowired
//    private ModelRepositoryDataJdbc modelRepositoryDataJdbc;
//    @Autowired
//    private EngineRepositoryDataJdbc engineRepositoryDataJdbc;
//
//    public Specification<CarPart> getPredicateByFilter(FilterCarPart filterCarPart) {
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
//    }
//
//    private Optional<Predicate> getEnginePredicate(Path<Object> engine, FilterCarPart filterCarPart) {
//        if (Objects.nonNull(filterCarPart.getEngineName()) && !filterCarPart.getEngineName().isEmpty()) {
//            Example<Engine> exampleEngine = Example.of(Engine.builder()
//                    .name(filterCarPart.getEngineName())
//                    .build());
//
//            List<Engine> engineList = engineRepositoryDataJdbc.findAll(exampleEngine);
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
//            Example<Model> exampleModel = Example.of(Model.builder()
//                    .name(isModuleName ? filterCarPart.getModelName() : null)
//                    .yearRelease(isModuleYear ? filterCarPart.getYearRelease() : null)
//                    .build());
//
//            List<Model> modelList = modelRepositoryDataJdbc.findAll(exampleModel);
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
//            Example<Brand> exampleBrand = Example.of(Brand.builder()
//                    .name(filterCarPart.getBrandName())
//                    .build());
//
//            List<Brand> brandLst = brandRepositoryDataJdbc.findAll(exampleBrand);
//            if (brandLst.isEmpty()) {
//                return Optional.of(brand.isNull());
//            } else {
//                return Optional.ofNullable(brand.in(brandLst));
//            }
//        }
//
//        return Optional.empty();
//    }
//}
