package ru.hw.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.hw.demo.domain.CarPart;

import java.util.Optional;

@Repository
public interface CarPartRepositoryDataJdbc extends CrudRepository<CarPart, Long>/*, JpaSpecificationExecutor<CarPart>*/ {

    /**
     * @param vendorCode каталожный номер
     * @return возвращает найденные автозапчасти по {@code vendorCode}, иначе Optional.empty()
     */
    Optional<CarPart> findByVendorCode(String vendorCode);

    // FIXME: 10.08.2023 Решить N+1
//    /**
//     * @param brandsId список идентификаторов марок
//     * @return возвращает найденный список автозапчастей по {@code brandsId}.
//     */
//    Collection<CarPart> findAllByBrandRefIn(Collection<Long> brandsId);
//
//    // FIXME: 10.08.2023 Решить N+1
//    /**
//     * @param modelsId список идентификаторов моделей
//     * @return возвращает найденный список автозапчастей по {@code modelsId}.
//     */
//    Collection<CarPart> findAllByModelRefIn(Collection<Long> modelsId);
//
//    // FIXME: 10.08.2023 Решить N+1
//    /**
//     * @param enginesId список идентификаторов двигателей
//     * @return возвращает найденный список автозапчастей по {@code enginesId}.
//     */
//    Collection<CarPart> findAllByEngineRefIn(Collection<Long> enginesId);
}
