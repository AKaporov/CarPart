package ru.hw.demo.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import ru.hw.demo.domain.CarPart;

import java.util.Optional;

public interface CarPartRepositoryDataJdbc extends CrudRepository<CarPart, Long>, JpaSpecificationExecutor<CarPart> {

    /**
     * @param vendorCode каталожный номер
     * @return возвращает найденные автозапчасти по {@code vendorCode}, иначе Optional.empty()
     */
    Optional<CarPart> findByVendorCode(String vendorCode);
}
