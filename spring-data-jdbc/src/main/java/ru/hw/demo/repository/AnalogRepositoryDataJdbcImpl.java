package ru.hw.demo.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.hw.demo.constant.MainConstant;
import ru.hw.demo.domain.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class AnalogRepositoryDataJdbcImpl implements AnalogRepositoryDataJdbc {
    private final JdbcTemplate jdbcTemplate;

    private final BrandRepositoryDataJdbc brandRepositoryDataJdbc;
    private final CarPartRepositoryDataJdbc carPartRepositoryDataJdbc;

    @Override
    public Optional<Analog> findById(Long id) {
        List<Analog> analogList = jdbcTemplate.query(MainConstant.SQL_SELECT_ANALOG_WITH_ALL_RELATED_INFO, this::mapRowToAnalog, id);

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
//        CarPart carPart = new CarPart(resultSet.getLong("car_part_id"),
//                resultSet.getString("car_part_vendor_code"),
//                resultSet.getString("car_part_sku"),
//                resultSet.getString("car_part_name"),
//                resultSet.getString("car_part_description"),
//                resultSet.getDouble("car_part_price"),
//                resultSet.getString("car_part_manufacturer"),
//                resultSet.getDouble("car_part_rating"),
//                brand::getId,
//                model::getId,
//                engine::getId,
//                () -> country.getId(),
//                new HashSet<>(1),
//                new HashSet<>(1));
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
    public List<Analog> saveAll(List<Analog> analogList) {
        return analogList.stream()
                .map(this::save)
                .peek(System.out::println)
                .collect(Collectors.toCollection(() -> new ArrayList<>(analogList.size())));
    }

    @Override
    public void deleteAll() {
        Set<CarPart> carPartSet = getCarPartsForDeleteAll();

        deleteAnalogAll();
        deleteAllBrand(carPartSet);

//        deleteAllModel(carPartSet);
//        List<Model> modelList = new ArrayList<>(carPartSet.size());
//        carPartSet.stream().forEach(cp -> modelList.add(cp.getModel()));
//
//        deleteAllEngine();
//        List<Engine> engineList = new ArrayList<>(carPartSet.size());
//        carPartSet.stream().forEach(cp -> engineList.add(cp.getEngine()));
//
//        deleteCarPartAll();
    }

    private void deleteAllBrand(Set<CarPart> carPartSet) {
        Set<Brand> brandList = getBrandToDelete(carPartSet);
        brandRepositoryDataJdbc.deleteAll(brandList);
    }

    private Set<Brand> getBrandToDelete(Set<CarPart> carPartSet) {
        Set<Long> brandsId = new LinkedHashSet<>();
        carPartSet.stream().forEach(cp -> brandsId.add(cp.getBrandRef().getId()));
        List<Brand> foundBrands = brandRepositoryDataJdbc.findAllById(brandsId);
        Set<CarPart> foundCarPartsWithUsedBrands = carPartRepositoryDataJdbc.findAllByBrandRef(foundBrands);

        Map<Long, Brand> foundBrandsMap = foundBrands.stream()
                .collect(Collectors.toMap(Brand::getId, Function.identity()));

        Set<Brand> resultSet = new HashSet<>(carPartSet.size());
        carPartSet.stream()
                .filter(foundCarPartsWithUsedBrands::contains)
                .map(CarPart::getBrandRef)
                .forEach(b -> {
                    Brand brand = foundBrandsMap.get(b.getId());
                    resultSet.add(Brand.builder()
                            .id(brand.getId())
                            .name(brand.getName())
                            .build());
                });


        return resultSet;
    }

    private Set<CarPart> getCarPartsForDeleteAll() {
        List<Analog> allAnalogs = findAll();

        return allAnalogs.stream()
                .map(Analog::getCarPart)
                .collect(Collectors.toSet());
    }

    private List<Analog> findAll() {
        return jdbcTemplate.query(MainConstant.SQL_SELECT_ANALOG_ALL,
                this::mapRowToAnalog);
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
