package ru.hw.demo.repository;

import org.springframework.data.repository.CrudRepository;
import ru.hw.demo.domain.Photo;

import java.util.List;

public interface PhotoRepositoryDataJdbc extends CrudRepository<Photo, Long> {
    List<Photo> findAll();
}
