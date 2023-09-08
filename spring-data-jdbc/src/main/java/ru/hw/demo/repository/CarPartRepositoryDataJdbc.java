package ru.hw.demo.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.hw.demo.domain.CarPart;
import ru.hw.demo.repository.row_mapper.CarPartFindByVendorCodeRowMapper;

import java.util.Optional;

@Repository
public interface CarPartRepositoryDataJdbc extends CrudRepository<CarPart, Long>/*, CustomCarPartRepositoryDataJdbc , JpaSpecificationExecutor<CarPart>*/ {

    /**
     * @param vendorCode каталожный номер
     * @return возвращает найденные автозапчасти по {@code vendorCode}, иначе Optional.empty()
     */
    @Query(name = "CarPart.getByVendorCode",  // тело запроса в resource "jdbc-named-queries.properties"
            rowMapperClass = CarPartFindByVendorCodeRowMapper.class
//            resultSetExtractorClass = CarPartFindByVendorCodeResultSetExtractor.class
    )
    Optional<CarPart> findByVendorCode(String vendorCode);

    // FIXME: 08.09.2023 Может быть пригодится )
//    @Override
//    @Query(name = "CarPart.getCarPartById",  // тело запроса в resource "jdbc-named-queries.properties"
//            rowMapperClass = CarPartFindByVendorCodeRowMapper.class
//    )
//    Optional<CarPart> findById(Long id);

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
