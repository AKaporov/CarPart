package ru.hw.demo.repository;

import org.springframework.data.repository.Repository;
import ru.hw.demo.domain.Brand;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface BrandRepositoryDataJdbc extends Repository<Brand, Long> {
    List<Brand> findAll();

    void deleteAll();

    void deleteAll(Set<Brand> brands);

    Brand insert(Brand brand);

    Optional<Brand> findById(long id);

    List<Brand> findAllById(Set<Long> ids);

    Optional<Brand> findByName(String bandName);

    List<Brand> findAllByNames(Set<String> names);
}
