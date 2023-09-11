package ru.hw.demo.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.hw.demo.domain.CarPart;
import ru.hw.demo.repository.row_mapper.CarPartFindByVendorCodeRowMapper;

import java.util.Optional;

@Repository
public interface CarPartRepositoryDataJdbc extends CrudRepository<CarPart, Long> {

    /**
     * @param vendorCode каталожный номер
     * @return возвращает найденные автозапчасти по {@code vendorCode}, иначе Optional.empty()
     */
    @Query(name = "CarPart.getByVendorCode",  // тело запроса в resource "jdbc-named-queries.properties"
            rowMapperClass = CarPartFindByVendorCodeRowMapper.class
//            resultSetExtractorClass = CarPartFindByVendorCodeResultSetExtractor.class
    )
    Optional<CarPart> findByVendorCode(String vendorCode);
}
