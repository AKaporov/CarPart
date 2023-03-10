package ru.hw.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.hw.demo.domain.CarPart;

import java.util.Optional;

@Repository
public interface CarPartRepository extends JpaRepository<CarPart, Long>, JpaSpecificationExecutor<CarPart> {

    /**
     * @param vendorCode каталожный номер
     * @return возвращает найденные автозапчасти по {@code vendorCode}, иначе Optional.empty()
     */
    Optional<CarPart> findByVendorCode(String vendorCode);
}
