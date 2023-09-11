package ru.hw.demo.repository;

import com.querydsl.core.types.Predicate;
import ru.hw.demo.domain.CarPart;

import java.util.List;

public interface CustomCarPartRepositoryDataJdbc {
    List<CarPart> findAll(Predicate where);
}
