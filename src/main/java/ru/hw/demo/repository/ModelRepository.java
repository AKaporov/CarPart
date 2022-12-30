package ru.hw.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hw.demo.domain.Model;

@Repository
public interface ModelRepository extends JpaRepository<Model, Long> {
}
