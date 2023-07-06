package ru.hw.demo.repository;

import org.springframework.data.repository.CrudRepository;
import ru.hw.demo.domain.Country;

public interface CountryRepositoryJdbc extends CrudRepository<Country, Long> {
}
