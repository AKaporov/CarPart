package ru.hw.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.hw.demo.domain.Brand;
import ru.hw.demo.domain.CarPart;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface CarPartRepositoryDataJdbc extends CrudRepository<CarPart, Long>/*, JpaSpecificationExecutor<CarPart>*/ {

    /**
     * @param vendorCode каталожный номер
     * @return возвращает найденные автозапчасти по {@code vendorCode}, иначе Optional.empty()
     */
    Optional<CarPart> findByVendorCode(String vendorCode);

    /**
     * @param brands список моделей
     * @return возвращает найденный список автозапчастей по {@code brands}.
     */
    Set<CarPart> findAllByBrandRef(List<Brand> brands);
}
