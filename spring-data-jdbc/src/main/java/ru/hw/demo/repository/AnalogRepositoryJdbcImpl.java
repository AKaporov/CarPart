package ru.hw.demo.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.hw.demo.domain.Analog;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AnalogRepositoryJdbcImpl implements AnalogRepositoryJdbc {
    @Override
    public Optional<Analog> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Analog> saveAll(List<Analog> analogList) {
        return null;
    }

    @Override
    public void deleteAll() {

    }
}
