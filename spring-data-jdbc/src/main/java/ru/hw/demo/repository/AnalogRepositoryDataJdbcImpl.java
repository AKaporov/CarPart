package ru.hw.demo.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.hw.demo.constant.MainConstant;
import ru.hw.demo.domain.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AnalogRepositoryDataJdbcImpl implements AnalogRepositoryDataJdbc {
    private final JdbcTemplate jdbcTemplate;

    private final CarPartRepositoryDataJdbc carPartRepositoryDataJdbc;
    private final BrandRepositoryDataJdbc brandRepositoryDataJdbc;
    private final ModelRepositoryDataJdbc modelRepositoryDataJdbc;
    private final EngineRepositoryDataJdbc engineRepositoryDataJdbc;

    @Override
    public Optional<Analog> findById(Long id) {
        List<Analog> analogList = jdbcTemplate.query(MainConstant.SQL_SELECT_ANALOG_WITHOUT_PHOTO, this::mapRowToAnalog, id);

        return analogList.isEmpty() ? Optional.empty() : Optional.ofNullable(analogList.get(0));
    }

    private Analog mapRowToAnalog(ResultSet resultSet, int i) throws SQLException {
        Brand brand = Brand.builder()
                .id(resultSet.getLong("brand_id"))
                .name(resultSet.getString("brand_name"))
                .build();
        Model model = Model.builder()
                .id(resultSet.getLong("model_id"))
                .name(resultSet.getString("model_name"))
                .yearRelease(resultSet.getInt("model_year_release"))
                .build();
        Engine engine = Engine.builder()
                .id(resultSet.getLong("engine_id"))
                .name(resultSet.getString("engine_name"))
                .build();
        Country country = Country.builder()
                .id(resultSet.getLong("country_id"))
                .name(resultSet.getString("country_name"))
                .build();
        CarPart carPart = CarPart.builder()
                .id(resultSet.getLong("car_part_id"))
                .vendorCode(resultSet.getString("car_part_vendor_code"))
                .sku(resultSet.getString("car_part_sku"))
                .name(resultSet.getString("car_part_name"))
                .description(resultSet.getString("car_part_description"))
                .price(resultSet.getDouble("car_part_price"))
                .manufacturer(resultSet.getString("car_part_manufacturer"))
                .rating(resultSet.getDouble("car_part_rating"))
                .brandRef(brand::getId)
                .modelRef(model::getId)
                .engineRef(engine::getId)
                .countryRef(() -> country.getId())
                .photos(new HashSet<>(1))
                .analogs(new HashSet<>(1))
                .build();
        return Analog.builder()
                .id(resultSet.getLong("analog_id"))
                .vendor(resultSet.getString("analog_vendor"))
                .carPart(carPart)
                .build();
    }

    @Override
    @Transactional
    public List<Analog> saveAll(List<Analog> analogList) {
        return saveList(analogList);
    }

    @Override
    public void deleteAll() {
        deleteAnalogAll();
    }

    public List<Analog> findAll() {
        return jdbcTemplate.query(MainConstant.SQL_SELECT_ANALOG_ALL_WITHOUT_PHOTO, this::mapRowToAnalog);
    }

    private void deleteAnalogAll() {
        jdbcTemplate.update("delete from car_part_analogs where analog_id > 0");
        jdbcTemplate.update("delete from analogs where id > 0");
    }

    private Analog save(Analog analog) {
        jdbcTemplate.update(MainConstant.SQL_INSERT_ANALOG,
                analog.getCarPart().getId(),
                analog.getVendor()
        );

        Long analogId = findIdByCarPartIdAndVendor(analog.getCarPart().getId(), analog.getVendor()).orElse(null);
        analog.setId(analogId);

        return analog;
    }

    private List<Analog> saveList(List<Analog> analogs) {
        jdbcTemplate.batchUpdate(MainConstant.SQL_INSERT_ANALOG,
                analogs,
                analogs.size(),  //MainConstant.BATCH_SIZE_INSERT // Usually, the recommended batch size is 50-100, but it highly depends on our database server configurations and the size of each batch package.
                (PreparedStatement ps, Analog analog) -> {
                    ps.setLong(1, analog.getCarPart().getId());
                    ps.setString(2, analog.getVendor());
                }
        );

        List<Analog> resultList = new ArrayList<>(analogs.size());
        analogs.stream()
                .forEach(analog -> {
                    Long analogId = findIdByCarPartIdAndVendor(analog.getCarPart().getId(), analog.getVendor()).orElse(null);
                    analog.setId(analogId);

                    resultList.add(analog);

                });

        return resultList;
    }

    private Optional<Long> findIdByCarPartIdAndVendor(Long carPartId, String vendor) {
        List<Long> resultList = jdbcTemplate.query("select a.id as analog_id" +
                        "  from analogs a" +
                        " where a.CAR_PART_ID = ?" +
                        "   and a.vendor      = ?",
                this::mapRowToLong,
                carPartId,
                vendor
        );
        return resultList.isEmpty() ? Optional.empty() : Optional.ofNullable(resultList.get(0));
    }

    private Long mapRowToLong(ResultSet resultSet, int i) throws SQLException {
        return resultSet.getLong("analog_id");
    }
}
