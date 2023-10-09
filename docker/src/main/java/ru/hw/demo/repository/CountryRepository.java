package ru.hw.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hw.demo.domain.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
}
