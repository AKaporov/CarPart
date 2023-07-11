package ru.hw.demo.repository;

import org.springframework.data.repository.Repository;
import ru.hw.demo.domain.Brand;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface BrandRepositoryDataJdbc extends Repository<Brand, Long> {
    List<Brand> findAll();

    void deleteAll();

    Brand insert(Brand brand);

    Optional<Brand> findById(long id);

    Optional<Brand> findByName(String bandName);

    List<Brand> findAllByNames(Set<String> names);
}
