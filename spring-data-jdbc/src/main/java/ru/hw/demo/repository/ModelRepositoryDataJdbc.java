package ru.hw.demo.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.repository.CrudRepository;
import ru.hw.demo.domain.Model;

import java.util.List;

public interface ModelRepositoryDataJdbc extends CrudRepository<Model, Long> {
    List<Model> findAll(Example<Model> example);
}
