package ru.hw.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.hw.demo.domain.CarPart;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface CarPartRepositoryDataJdbc extends CrudRepository<CarPart, Long>/*, JpaSpecificationExecutor<CarPart>*/ {

    /**
     * @param vendorCode каталожный номер
     * @return возвращает найденные автозапчасти по {@code vendorCode}, иначе Optional.empty()
     */
    Optional<CarPart> findByVendorCode(String vendorCode);

    /**
     * @param brandsId список идентификаторов моделей
     * @return возвращает найденный список автозапчастей по {@code brandsId}.
     */
    Collection<CarPart> findAllByBrandRefIn(Collection<Long> brandsId);
}
