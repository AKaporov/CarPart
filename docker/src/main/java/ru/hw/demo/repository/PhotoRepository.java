package ru.hw.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hw.demo.domain.Photo;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {
}
