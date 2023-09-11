package ru.hw.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.hw.demo.domain.Engine;

import java.util.List;

@Repository
public interface EngineRepositoryDataJdbc extends CrudRepository<Engine, Long> {

    List<Engine> findAllByName(String name);
}
