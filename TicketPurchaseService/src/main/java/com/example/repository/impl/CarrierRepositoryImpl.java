package com.example.repository.impl;

import com.example.domain.Carrier;
import com.example.repository.CarrierRepository;
import com.example.repository.mapper.CarrierRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CarrierRepositoryImpl implements CarrierRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final CarrierRowMapper carrierRowMapper;


    @Override
    public Optional<Carrier> findById(Long id) {
        String sql = """
                SELECT
                    c.id as carrier_id,
                    c.name as carrier_name,
                    c.phone as carrier_phone
                FROM carriers c
                WHERE c.id = :id AND c.deleted_at IS NULL
                """;
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(
                            sql,
                            new MapSqlParameterSource("id", id),
                            carrierRowMapper
                    )
            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Carrier> findPage(Integer page, Integer size) {

        MapSqlParameterSource params = new MapSqlParameterSource();

        String sql = """
                SELECT
                    c.id as carrier_id,
                    c.name as carrier_name,
                    c.phone as carrier_phone
                FROM carriers c
                WHERE c.deleted_at IS NULL
                """ + " LIMIT :size OFFSET :offset";
        params.addValue("size", size);
        params.addValue("offset", page * size);

        return jdbcTemplate.query(sql, params, carrierRowMapper);
    }

    @Override
    public Carrier save(Carrier carrier) {
        String sql = """
                INSERT INTO carriers (name, phone)
                VALUES (:name, :phone)
                RETURNING id
                """;

        Long id = jdbcTemplate.queryForObject(
                sql,
                new MapSqlParameterSource()
                        .addValue("name", carrier.getName())
                        .addValue("phone", carrier.getPhone()),
                Long.class
        );

        carrier.setId(id);
        return carrier;
    }

    @Override
    public void update(Carrier carrier) {
        String sql = """
                UPDATE carriers SET
                    name = :name,
                    phone = :phone
                WHERE id = :id
                """;

        jdbcTemplate.update(
                sql,
                new MapSqlParameterSource()
                        .addValue("name", carrier.getName())
                        .addValue("phone", carrier.getPhone())
                        .addValue("id", carrier.getId())
        );
    }

    @Override
    public void delete(Long id) {
        String sql = """
                UPDATE carriers SET
                    deleted_at = NOW()
                WHERE id = :id AND deleted_at IS NULL
                """;
        jdbcTemplate.update(sql, new MapSqlParameterSource("id", id));
    }

    @Override
    public Long count() {
        String sql = "SELECT COUNT(*) FROM carriers c WHERE c.deleted_at IS NULL";
        return jdbcTemplate.queryForObject(sql, new MapSqlParameterSource(), Long.class);
    }
}
