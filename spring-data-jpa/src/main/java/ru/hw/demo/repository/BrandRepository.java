package ru.hw.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hw.demo.domain.Brand;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
}
