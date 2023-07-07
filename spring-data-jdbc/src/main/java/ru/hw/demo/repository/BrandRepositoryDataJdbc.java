package ru.hw.demo.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.repository.Repository;
import ru.hw.demo.domain.Brand;

import java.util.List;
import java.util.Optional;

public interface BrandRepositoryDataJdbc extends Repository<Brand, Long> {
    List<Brand> findAll(Example<Brand> example);

    void deleteAll();

    Brand save(Brand brand);

    Optional<Brand> findById(long id);
}
