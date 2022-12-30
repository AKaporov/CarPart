package ru.hw.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hw.demo.domain.Engine;

@Repository
public interface EngineRepository extends JpaRepository<Engine, Long> {
}
