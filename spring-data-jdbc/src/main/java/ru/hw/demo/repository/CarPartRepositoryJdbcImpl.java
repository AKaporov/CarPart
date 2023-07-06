package ru.hw.demo.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.hw.demo.domain.CarPart;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CarPartRepositoryJdbcImpl implements CarPartRepositoryJdbc{
    @Override
    public Optional<CarPart> findByVendorCode(String vendorCode) {
        return Optional.empty();
    }
}
