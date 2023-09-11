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

    public Predicate getPredicateByFilter(FilterCarPart filterCarPart) {
        BooleanExpression where = null;

        if (Objects.nonNull(filterCarPart.getBrandName()) && !filterCarPart.getBrandName().isEmpty()) {
            where = brands.name.eq(filterCarPart.getBrandName());
        }

        boolean isModuleName = Objects.nonNull(filterCarPart.getModelName()) && !filterCarPart.getModelName().isEmpty();
        if (isModuleName) {
            BooleanExpression predicateModelName = models.name.eq(filterCarPart.getModelName());
            where = Objects.nonNull(where) ? where.and(predicateModelName) : predicateModelName;
        }

        boolean isModuleYear = Objects.nonNull(filterCarPart.getYearRelease()) && filterCarPart.getYearRelease() != 0;
        if (isModuleYear) {
            BooleanExpression predicateModelYearRelease = models.yearRelease.eq(filterCarPart.getYearRelease());
            where = Objects.nonNull(where) ? where.and(predicateModelYearRelease) : predicateModelYearRelease;
        }

        if (Objects.nonNull(filterCarPart.getEngineName()) && !filterCarPart.getEngineName().isEmpty()) {
            BooleanExpression predicateEngineName = engines.name.eq(filterCarPart.getEngineName());
            where = Objects.nonNull(where) ? where.and(predicateEngineName) : predicateEngineName;
        }

        return where;
    }

}
