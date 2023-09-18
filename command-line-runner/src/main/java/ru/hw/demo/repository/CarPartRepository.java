package ru.hw.demo.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.hw.demo.domain.CarPart;

import java.util.Optional;

@Repository
public interface CarPartRepository extends JpaRepository<CarPart, Long>, JpaSpecificationExecutor<CarPart> {

    /**
     * Если не использовать "analogList" в аннотации @EntityGraph, то будет два Правильных запроса.
     * Первый в CarPart и сущности перечисленные в attributePaths. Второй запрос analogs по car_part_id
     * @param vendorCode каталожный номер
     * @return возвращает найденные автозапчасти по {@code vendorCode}, иначе Optional.empty()
     */
    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, attributePaths = {"brand", "model", "engine", "country", "photoList"})
    Optional<CarPart> findByVendorCode(String vendorCode);
}
