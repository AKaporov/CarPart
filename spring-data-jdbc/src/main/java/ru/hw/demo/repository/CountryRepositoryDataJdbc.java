package ru.hw.demo.repository;

import org.springframework.data.repository.CrudRepository;
import ru.hw.demo.domain.Country;

public interface CountryRepositoryDataJdbc extends CrudRepository<Country, Long> {
}
