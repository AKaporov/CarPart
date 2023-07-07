package ru.hw.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.hw.demo.domain.Photo;

import java.util.List;
@Repository
public interface PhotoRepositoryDataJdbc extends CrudRepository<Photo, Long> {
    List<Photo> findAll();
}
