package ru.hw.demo.repository;

import ru.hw.demo.domain.CarPart;

import java.util.Optional;

public interface CarPartRepositoryJdbc /*extends  CrudRepository<CarPart, Long>, JpaSpecificationExecutor<CarPart> */ {

    /**
     * @param vendorCode каталожный номер
     * @return возвращает найденные автозапчасти по {@code vendorCode}, иначе Optional.empty()
     */
    Optional<CarPart> findByVendorCode(String vendorCode);
}
