package ru.hw.demo.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.hw.demo.domain.Brand;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BrandRepositoryDataJdbcImpl implements BrandRepositoryDataJdbc {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Brand> findAll(Example<Brand> example) {
        return null;
    }

    @Override
    public void deleteAll() {

    }

    @Override
    public Brand save(Brand brand) {
        return null;
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

    private Brand mapRowToBrand(ResultSet resultSet, int i) throws SQLException {
        return Brand.builder()
                .id(resultSet.getLong("brand_id"))
                .name(resultSet.getString("brand_name"))
                .build();
    }
}
