package ru.hw.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.hw.demo.domain.Country;

@Repository
public interface CountryRepositoryDataJdbc extends CrudRepository<Country, Long> {
}
