package ru.hw.demo.repository.row_mapper;

import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.hw.demo.domain.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * RowMapper: Когда каждая строка результирующего набора сопоставляется объекту домена, может быть реализована
 * как частный внутренний класс.
 */

@Component
public class CarPartFindByVendorCodeRowMapper implements RowMapper<CarPart> {
    @Override
    public CarPart mapRow(ResultSet rs, int rowNum) throws SQLException {
        Brand brand = Brand.builder()
                .id(rs.getLong("brand_id"))
                .name(rs.getString("brand_name"))
                .build();
        Model model = Model.builder()
                .id(rs.getLong("model_id"))
                .name(rs.getString("model_name"))
                .yearRelease(rs.getInt("model_year_release"))
                .build();
        Engine engine = Engine.builder()
                .id(rs.getLong("engine_id"))
                .name(rs.getString("engine_name"))
                .build();
        Country country = Country.builder()
                .id(rs.getLong("country_id"))
                .name(rs.getString("country_name"))
                .build();
        CarPart.CarPartBuilder carPartBuilder = CarPart.builder()
                .id(rs.getLong("car_part_id"))
                .vendorCode(rs.getString("car_part_vendor_code"))
                .sku(rs.getString("car_part_sku"))
                .name(rs.getString("car_part_name"))
                .description(rs.getString("car_part_description"))
                .price(rs.getDouble("car_part_price"))
                .manufacturer(rs.getString("car_part_manufacturer"))
                .rating(rs.getDouble("car_part_rating"))
                .brandRef(AggregateReference.to(getId(rs, "car_part_brand_id")))
                .brandQueryDsl(brand)
                .modelRef(AggregateReference.to(getId(rs, "car_part_model_id_fk")))
                .modelQueryDsl(model)
                .engineRef(AggregateReference.to(getId(rs, "car_part_engine_id")))
                .engineQueryDsl(engine)
                .countryRef(AggregateReference.to(getId(rs, "car_part_country_id")))
                .countryQueryDsl(country);

        Set<Photo> photos = new LinkedHashSet<>();
        Set<AnalogRef> analogRefs = new LinkedHashSet<>();
        while (rs.next()) {
            Photo photo = Photo.builder()
                    .id(rs.getLong("photo_id"))
                    .photoUrl(rs.getString("photo_url"))
                    .build();
            if (!photos.contains(photo)) {
                photos.add(photo);
            }

            AnalogRef analogRef = AnalogRef.builder()
                    .analogId(rs.getLong("car_part_analogs_analog_id"))
                    .build();
            if (!analogRefs.contains(analogRef)) {
                analogRefs.add(analogRef);
            }
        }

        carPartBuilder
                .photos(photos)
                .analogs(analogRefs);

        return carPartBuilder.build();
    }

    private static long getId(ResultSet rs, String columnLabel) {
        try {
            return rs.getLong(columnLabel);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
