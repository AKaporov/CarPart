package ru.hw.demo.repository;

import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.QBean;
import com.querydsl.sql.SQLQueryFactory;
import jakarta.inject.Inject;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.hw.demo.domain.*;

import java.util.List;

import static com.querydsl.core.types.Projections.bean;
import static com.querydsl.example.sql.QBrands.brands;
import static com.querydsl.example.sql.QCarPartAnalogs.carPartAnalogs;
import static com.querydsl.example.sql.QCarParts.carParts;
import static com.querydsl.example.sql.QCountries.countries;
import static com.querydsl.example.sql.QEngines.engines;
import static com.querydsl.example.sql.QModels.models;
import static com.querydsl.example.sql.QPhotos.photos;


@Transactional
@Repository
public class CustomCarPartRepositoryDataJdbcImpl implements CustomCarPartRepositoryDataJdbc {

    // Bean создается в JdbcConfiguration.java
    @Inject
    private SQLQueryFactory queryFactory;

    private final QBean<Brand> brandQBean = bean(Brand.class, brands.id.as("id"), brands.name.as("name"));
    private final QBean<Model> modelQBean = bean(Model.class, models.all());
    private final QBean<Engine> engineQBean = bean(Engine.class, engines.all());
    private final QBean<Country> countryQBean = bean(Country.class, countries.all());
    private final QBean<Photo> photoQBean = bean(Photo.class, photos.photoId.as("id"), photos.url.as("photoUrl"));
    private final QBean<AnalogRef> analogRefQBean = bean(AnalogRef.class, carPartAnalogs.analogId.as("analogId"));


    private final QBean<CarPart> carPartQBean = bean(CarPart.class,
            carParts.carPartId.as("id"),  // alias на поле Бизнес Сущности "CarPart"
            carParts.vendorCode.as("vendorCode"),
            carParts.sku.as("sku"),
            carParts.name,
            carParts.description,
            carParts.price,
            carParts.manufacturer,
            carParts.rating,
            brandQBean.as("brandQueryDsl"),
            modelQBean.as("modelQueryDsl"),
            engineQBean.as("engineQueryDsl"),
            countryQBean.as("countryQueryDsl"),
            GroupBy.set(photoQBean).as("photos"),
            GroupBy.set(analogRefQBean).as("analogs")
    );


    @Override
    public List<CarPart> findAll(Predicate where) {
        return queryFactory
//                .select(carPartQBean)
                .from(carParts)
                .innerJoin(carParts.fk01Brand, brands)
                .innerJoin(carParts.fk02Model, models)
                .innerJoin(carParts.fk03Engine, engines)
                .innerJoin(carParts.constraintC67, countries)
                .leftJoin(carParts._fk01Photo, photos)
                .leftJoin(carParts._constraint54, carPartAnalogs)
                .where(where)
//                .fetch();
                .transform(GroupBy.groupBy(carParts.carPartId).list(carPartQBean));
    }

}
