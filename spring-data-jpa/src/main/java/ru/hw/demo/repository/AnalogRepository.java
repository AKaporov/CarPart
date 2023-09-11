package ru.hw.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hw.demo.domain.Analog;

@Repository
public interface AnalogRepository extends JpaRepository<Analog, Long> {
}
