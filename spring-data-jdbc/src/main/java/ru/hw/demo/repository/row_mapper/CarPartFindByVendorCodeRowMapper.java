package ru.hw.demo.repository.row_mapper;

import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.hw.demo.domain.AnalogRef;
import ru.hw.demo.domain.CarPart;
import ru.hw.demo.domain.Photo;

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
                .modelRef(AggregateReference.to(getId(rs, "car_part_model_id_fk")))
                .engineRef(AggregateReference.to(getId(rs, "car_part_engine_id")))
                .countryRef(AggregateReference.to(getId(rs, "car_part_country_id")));

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
