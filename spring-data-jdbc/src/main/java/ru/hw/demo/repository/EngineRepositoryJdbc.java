package ru.hw.demo.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.repository.CrudRepository;
import ru.hw.demo.domain.Engine;

import java.util.List;

public interface EngineRepositoryJdbc extends CrudRepository<Engine, Long> {

    List<Engine> findAll(Example<Engine> example);
}
