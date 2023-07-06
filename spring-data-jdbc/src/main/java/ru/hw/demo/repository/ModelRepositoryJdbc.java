package ru.hw.demo.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import ru.hw.demo.domain.Engine;
import ru.hw.demo.domain.Model;

import java.util.List;

public interface ModelRepositoryJdbc extends CrudRepository<Model, Long> {
    List<Model> findAll(Example<Model> example);
}
