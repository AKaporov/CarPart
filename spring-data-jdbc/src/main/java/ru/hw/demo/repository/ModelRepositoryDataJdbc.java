package ru.hw.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.hw.demo.domain.Model;

@Repository
public interface ModelRepositoryDataJdbc extends CrudRepository<Model, Long> {
//    List<Model> findAll(Example<Model> example);
}
