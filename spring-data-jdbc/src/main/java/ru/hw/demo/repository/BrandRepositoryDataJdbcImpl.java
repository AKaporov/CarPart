package ru.hw.demo.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.hw.demo.domain.Brand;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
@RequiredArgsConstructor
public class BrandRepositoryDataJdbcImpl implements BrandRepositoryDataJdbc {

    private final JdbcTemplate jdbcTemplate;  //в запросах используется ?
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;  //вместо ?

    @Override
    public List<Brand> findAllByNames(Set<String> names) {
        String sql = "select b.id as brand_id, b.name as brand_name from brands b where b.name in (:brand_names)";
        SqlParameterSource paramSource = new MapSqlParameterSource("brand_names", names);
        return namedParameterJdbcTemplate.query(sql, paramSource, this::mapRowToBrand);
    }

    @Override
    public List<Brand> findAll() {
        return jdbcTemplate.query("select b.id as brand_id, b.name as brand_name from brands b", this::mapRowToBrand);
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("delete from brands where id > 0");
    }

    @Override
    public void deleteAll(Set<Brand> brands) {
// FIXME: 10.08.2023 Сделать реализацию + тест
    }

    @Override
    public Brand insert(Brand brand) {
        namedParameterJdbcTemplate.update("insert into brands (name) " +
                "values (:name)", Map.of("name", brand.getName()));

        return findByName(brand.getName()).orElse(null);
    }

    @Override
    public Optional<Brand> findById(long id) {
        List<Brand> brandList = jdbcTemplate.query("select b.id as brand_id, b.name as brand_name" +
                        "  from brands b " +
                        " where b.id = ? ",
                this::mapRowToBrand,
                id);

        return brandList.isEmpty() ? Optional.empty() : Optional.ofNullable(brandList.get(0));
    }

    @Override
    public List<Brand> findAllById(Set<Long> ids) {
        if (ids.isEmpty()) {
            return new ArrayList<>(1);
        }
        String inSql = String.join(",", Collections.nCopies(ids.size(), "?"));
        String sql = String.format("select b.id as brand_id, b.name as brand_name from brands b where b.id in (%s)", inSql);
        return jdbcTemplate.query(sql, ids.toArray(), this::mapRowToBrand);
    }

    @Override
    public Optional<Brand> findByName(String bandName) {
        Map<String, String> params = Map.of("name", bandName);
        List<Brand> brandList = namedParameterJdbcTemplate.query(
                "select b.id as brand_id, b.name as brand_name from brands b where b.name = :name", params, this::mapRowToBrand);
        return brandList.isEmpty() ? Optional.empty() : Optional.ofNullable(brandList.get(0));
    }

    private Brand mapRowToBrand(ResultSet resultSet, int i) throws SQLException {
        return Brand.builder()
                .id(resultSet.getLong("brand_id"))
                .name(resultSet.getString("brand_name"))
                .build();
    }
}
