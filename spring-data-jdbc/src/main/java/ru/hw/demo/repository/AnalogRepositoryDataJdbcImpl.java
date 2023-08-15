package ru.hw.demo.repository;

import lombok.RequiredArgsConstructor;
import org.assertj.core.util.Sets;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.hw.demo.constant.MainConstant;
import ru.hw.demo.domain.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

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
        deleteAnalogAll();
    }

//    private void deleteAllEngine(Set<CarPart> carPartSet) {
//        Set<Engine> engines = getEngineToDelete(carPartSet);
//        engineRepositoryDataJdbc.deleteAll(engines);
//    }
//
//    /**
//     * По переданной {@code carPartSet} метод находит автозапчасти у которых двигатель не используется в других
//     * автозапчастях. Если указанная в {@code carPartSet} двигатель используется в Не переданной автозапчасти,
//     * то автозапчасть с таким двигателем не возвращается.
//     *
//     * @param carPartSet коллекция автозапчастей
//     * @return возвращает коллекцию двигателей, которые не используются в других автозапчастях
//     */
//    private Set<Engine> getEngineToDelete(Set<CarPart> carPartSet) {
//        // 1. По переданным моделям найти все автозапчасти
//        Set<Long> enginesId = new LinkedHashSet<>();
//        carPartSet.stream().forEach(cp -> enginesId.add(cp.getEngineRef().getId()));
//        Collection<CarPart> foundAllCarPartsWithUsedEngine = carPartRepositoryDataJdbc.findAllByEngineRefIn(enginesId);
//
//        if (foundAllCarPartsWithUsedEngine.isEmpty()) {
//            return new HashSet<>(1);
//        }
//
//        Map<Long, List<CarPart>> foundCarPartMap = foundAllCarPartsWithUsedEngine.stream()
//                .collect(Collectors.groupingBy(cp -> cp.getEngineRef().getId()));
//
//        // 2. Отобрать идентификаторы моделей, которые есть только в переданном списке и нет в остальных автозапчастях
//        Set<Long> resultEnginesId = new HashSet<>(enginesId.size());
//        enginesId.stream()
//                .forEach(engineId -> {
//                    List<CarPart> carParts = foundCarPartMap.get(engineId);
//
//                    carParts.removeAll(carPartSet);
//                    if (carParts.isEmpty()) {
//                        resultEnginesId.add(engineId);
//                    }
//                });
//
//        // 3. Вернуть отобранные марки
//        Iterable<Engine> allById = engineRepositoryDataJdbc.findAllById(resultEnginesId);
//        return Sets.newHashSet(allById);
//    }
//
//    private void deleteAllModel(Set<CarPart> carPartSet) {
//        Set<Model> models = getModelToDelete(carPartSet);
//        modelRepositoryDataJdbc.deleteAll(models);
//    }
//
//    /**
//     * По переданной {@code carPartSet} метод находит автозапчасти у которых модель автомобиля не используется в других
//     * автозапчастях. Если указанная в {@code carPartSet} модель автомобиля используется в Не переданной автозапчасти,
//     * то автозапчасть с такой моделью автомобиля не возвращается.
//     *
//     * @param carPartSet коллекция автозапчастей
//     * @return возвращает коллекцию моделей автомобилей, которые не используются в других автозапчастях
//     */
//    private Set<Model> getModelToDelete(Set<CarPart> carPartSet) {
//        // 1. По переданным моделям найти все автозапчасти
//        Set<Long> modelsId = new LinkedHashSet<>();
//        carPartSet.stream().forEach(cp -> modelsId.add(cp.getModelRef().getId()));
//        Collection<CarPart> foundAllCarPartsWithUsedModels = carPartRepositoryDataJdbc.findAllByModelRefIn(modelsId);
//
//        if (foundAllCarPartsWithUsedModels.isEmpty()) {
//            return new HashSet<>(1);
//        }
//
//        Map<Long, List<CarPart>> foundCarPartMap = foundAllCarPartsWithUsedModels.stream()
//                .collect(Collectors.groupingBy(cp -> cp.getModelRef().getId()));
//
//        // 2. Отобрать идентификаторы моделей, которые есть только в переданном списке и нет в остальных автозапчастях
//        Set<Long> resultModelsId = new HashSet<>(modelsId.size());
//        modelsId.stream()
//                .forEach(modelId -> {
//                    List<CarPart> carParts = foundCarPartMap.get(modelId);
//
//                    carParts.removeAll(carPartSet);
//                    if (carParts.isEmpty()) {
//                        resultModelsId.add(modelId);
//                    }
//                });
//
//        // 3. Вернуть отобранные марки
//        Iterable<Model> allById = modelRepositoryDataJdbc.findAllById(resultModelsId);
//        return Sets.newHashSet(allById);
//    }
//
//    private void deleteAllBrand(Set<CarPart> carPartSet) {
//        Set<Brand> brands = getBrandToDelete(carPartSet);
//        brandRepositoryDataJdbc.deleteAll(brands);
//    }
//
//    /**
//     * По переданной {@code carPartSet} метод находит автозапчасти у которых марка автомобиля не используется в других
//     * автозапчастях. Если указанная в {@code carPartSet} марка автомобиля используется в Не переданной автозапчасти,
//     * то автозапчасть с такой маркой автомобиля не возвращается.
//     *
//     * @param carPartSet коллекция автозапчастей
//     * @return возвращает коллекцию марок автомобилей, которые не используются в других автозапчастях
//     */
//    private Set<Brand> getBrandToDelete(Set<CarPart> carPartSet) {
//        // 1. По переданным маркам найти все автозапчасти
//        Set<Long> brandsId = new LinkedHashSet<>();
//        carPartSet.stream().forEach(cp -> brandsId.add(cp.getBrandRef().getId()));
//        Collection<CarPart> foundAllCarPartsWithUsedBrands = carPartRepositoryDataJdbc.findAllByBrandRefIn(brandsId);
//
//        if (foundAllCarPartsWithUsedBrands.isEmpty()) {
//            return new HashSet<>(1);
//        }
//
//        Map<Long, List<CarPart>> foundCarPartMap = foundAllCarPartsWithUsedBrands.stream()
//                .collect(Collectors.groupingBy(cp -> cp.getBrandRef().getId()));
//
//        // 2. Отобрать идентификаторы марок, которые есть только в переданном списке и нет в остальных автозапчастях
//        Set<Long> resultBrandsId = new HashSet<>(brandsId.size());
//        brandsId.stream()
//                .forEach(brandId -> {
//                    List<CarPart> carParts = foundCarPartMap.get(brandId);
//
//                    carParts.removeAll(carPartSet);
//                    if (carParts.isEmpty()) {
//                        resultBrandsId.add(brandId);
//                    }
//                });
//
//        // 3. Вернуть отобранные марки
//        return new HashSet<>(brandRepositoryDataJdbc.findAllById(resultBrandsId));
//    }
//
//    private Set<CarPart> getCarPartsForDeleteAll() {
//        List<Analog> allAnalogs = findAll();
//
//        return allAnalogs.stream()
//                .map(Analog::getCarPart)
//                .collect(Collectors.toSet());
//    }

    public List<Analog> findAll() {
        return jdbcTemplate.query(MainConstant.SQL_SELECT_ANALOG_ALL, this::mapRowToAnalog);
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
