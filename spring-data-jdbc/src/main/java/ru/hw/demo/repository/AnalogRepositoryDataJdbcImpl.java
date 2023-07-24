package ru.hw.demo.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.hw.demo.domain.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AnalogRepositoryDataJdbcImpl implements AnalogRepositoryDataJdbc {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Optional<Analog> findById(Long id) {
        List<Analog> analogList = jdbcTemplate.query("select a.id as analog_id, a.vendor as analog_vendor, " +
// car_parts
                "cp.car_part_id as car_part_id, cp.vendor_code as car_part_vendor_code, cp.sku as car_part_sku, " +
                "cp.name as car_part_name, cp.description as car_part_description, cp.price as car_part_price, " +
                "cp.manufacturer as car_part_manufacturer, cp.rating as car_part_rating, " +
// brands
                "b.id as brand_id, b.name as brand_name, " +
// models
                "m.id as model_id, m.name as model_name, m.year_release as model_year_release, " +
// engines
                "e.id as engine_id, e.name as engine_name, " +
// countries
                "c.id as country_id, c.name as country_name, " +
                " from analogs a " +
                "inner join car_parts cp " +
                "        on cp.car_part_id = a.car_part_id " +
                "inner join brands b " +
                "        on b.id = cp.brand_id " +
                "inner join models m " +
                "        on m.id = cp.model_id " +
                "inner join engines e " +
                "        on e.id = cp.engine_id " +
                "inner join countries c " +
                "        on c.id = cp.country_id" +
                " where a.id = ?", this::mapRowToAnalog, id);

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
        CarPart carPart = new CarPart(resultSet.getLong("car_part_id"),
                resultSet.getString("car_part_vendor_code"),
                resultSet.getString("car_part_sku"),
                resultSet.getString("car_part_name"),
                resultSet.getString("car_part_description"),
                resultSet.getDouble("car_part_price"),
                resultSet.getString("car_part_manufacturer"),
                resultSet.getDouble("car_part_rating"),
                brand::getId,
                model::getId,
                engine::getId,
                () -> country.getId(),
                new HashSet<>(1),
                new HashSet<>(1));
        return Analog.builder()
                .id(resultSet.getLong("analog_id"))
                .vendor(resultSet.getString("analog_vendor"))
                .carPart(carPart)
                .build();
    }

    @Override
    public List<Analog> saveAll(List<Analog> analogList) {
        return null;
    }

    @Override
    public void deleteAll() {
//        List<CarPart> carPartList = getCarPartListForDeleteAll();
//
//        deleteAllBrand(carPartList);
//        List<Brand> brandList = new ArrayList<>(carPartList.size());
//        carPartList.stream().forEach(cp -> brandList.add(cp.getBrand()));
//
//        deleteAllModel(carPartList);
//        List<Model> modelList = new ArrayList<>(carPartList.size());
//        carPartList.stream().forEach(cp -> modelList.add(cp.getModel()));
//
//        deleteAllEngine();
//        List<Engine> engineList = new ArrayList<>(carPartList.size());
//        carPartList.stream().forEach(cp -> engineList.add(cp.getEngine()));
//
//        deleteCarPartAll();
//        deleteCarPartAnalogAll();
        deleteAnalogAll();
    }

    private void deleteAnalogAll() {
        jdbcTemplate.update("delete from car_part_analogs where analog_id > 0");
        jdbcTemplate.update("delete from analogs where id > 0");
    }
}
