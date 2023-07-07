package ru.hw.demo.repository;

import org.springframework.data.repository.Repository;
import ru.hw.demo.domain.Analog;

import java.util.List;
import java.util.Optional;

public interface AnalogRepositoryDataJdbc extends Repository<Analog, Long> {

    Optional<Analog> findById(Long id);

    List<Analog> saveAll(List<Analog> analogList);

    void deleteAll();
}
