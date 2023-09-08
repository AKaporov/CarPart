package ru.hw.demo.repository.result_set_extractor;

import org.h2.jdbc.JdbcResultSet;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;
import ru.hw.demo.domain.AnalogRef;
import ru.hw.demo.domain.CarPart;
import ru.hw.demo.domain.Photo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * ResultSetExtractor: Когда несколько строк результирующего набора сопоставляются с одним объектом.
 * Например, при выполнении сложных объединений в запросе может потребоваться доступ ко всему результирующему набору…
 */

@Component
public class CarPartFindByVendorCodeResultSetExtractor implements ResultSetExtractor<List<CarPart>> {
    @Override
    public List<CarPart> extractData(ResultSet rs) throws SQLException, DataAccessException {
        // TODO: 18.08.2023 !!!!! Реализация не доделана !!!!!, потому что для запроса
        //  CarPartRepositoryDataJdbc.findByVendorCode используется CarPartFindByVendorCodeRowMapper
        long rowCount = ((JdbcResultSet) rs).getResult().getRowCount();
        List<CarPart> resultList = new ArrayList<>((int) rowCount);

//        Map<Long, List<String>> data = new LinkedHashMap<>();

        Set<Photo> photos = new LinkedHashSet<>();
        Set<AnalogRef> analogRefs = new LinkedHashSet<>();
        while (rs.next()) {
//            long photoId = rs.getLong("photo_id");
//            data.putIfAbsent(photoId, new ArrayList<>());
//
//            String photoUrl = rs.getString("photo_url");
//            data.get(photoId).add(photoUrl);

            Photo photo = Photo.builder()
                    .id(rs.getLong("photo_id"))
                    .photoUrl(rs.getString("photo_url"))
                    .build();
            photos.add(photo);

            AnalogRef analogRef = AnalogRef.builder()
                    .analogId(rs.getLong("car_part_analogs_analog_id"))
                    .build();
            analogRefs.add(analogRef);

            resultList.add(CarPart.builder()
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
                    .countryRef(AggregateReference.to(getId(rs, "car_part_country_id")))
                    .photos(photos)
                    .analogs(analogRefs)
                    .build());
        }

        return resultList;
    }

    private static long getId(ResultSet rs, String columnLabel) {
        try {
            return rs.getLong(columnLabel);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
