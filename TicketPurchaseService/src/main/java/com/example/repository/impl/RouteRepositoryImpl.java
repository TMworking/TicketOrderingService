package com.example.repository.impl;

import com.example.domain.Route;
import com.example.repository.RouteRepository;
import com.example.repository.mapper.RouteRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RouteRepositoryImpl implements RouteRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final RouteRowMapper routeRowMapper;

    @Override
    public Optional<Route> findById(Long id) {
        String sql = """
                SELECT
                    r.id as route_id,
                    r.departure_point,
                    r.destination_point,
                    r.minutes_duration,
                    c.id as carrier_id,
                    c.name as carrier_name,
                    c.phone as carrier_phone
                FROM routes r
                LEFT JOIN carriers c ON r.carrier_id = c.id
                WHERE r.id = :id AND r.deleted_at IS NULL
                """;
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(
                            sql,
                            new MapSqlParameterSource("id", id),
                            routeRowMapper
                    )
            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Route> findPage(Integer page, Integer size) {

        MapSqlParameterSource params = new MapSqlParameterSource();

        String sql = """
                SELECT
                    r.id as route_id,
                    r.departure_point,
                    r.destination_point,
                    r.minutes_duration,
                    c.id as carrier_id,
                    c.name as carrier_name,
                    c.phone as carrier_phone
                FROM routes r
                LEFT JOIN carriers c ON r.carrier_id = c.id
                WHERE r.deleted_at IS NULL
                """ + " LIMIT :size OFFSET :offset";
        params.addValue("size", size);
        params.addValue("offset", page * size);

        return jdbcTemplate.query(sql, params, routeRowMapper);
    }

    @Override
    public Route save(Route route) {
        String sql = """
            INSERT INTO routes (departure_point, destination_point, minutes_duration, carrier_id)
            VALUES (:departurePoint, :destinationPoint, :minutesDuration, :carrierId)
            RETURNING id
            """;

        Long id = jdbcTemplate.queryForObject(
                sql,
                new MapSqlParameterSource()
                        .addValue("departurePoint", route.getDeparturePoint())
                        .addValue("destinationPoint", route.getDestinationPoint())
                        .addValue("minutesDuration", route.getMinutesDuration())
                        .addValue("carrierId", route.getCarrier().getId()),
                Long.class
        );

        route.setId(id);
        return route;
    }

    @Override
    public void update(Route route) {
        String sql = """
            UPDATE routes SET
                departure_point = :departurePoint,
                destination_point = :destinationPoint,
                minutes_duration = :minutesDuration,
                carrier_id = :carrierId
            WHERE id = :id
            """;

        jdbcTemplate.update(
                sql,
                new MapSqlParameterSource()
                        .addValue("departurePoint", route.getDeparturePoint())
                        .addValue("destinationPoint", route.getDestinationPoint())
                        .addValue("minutesDuration", route.getMinutesDuration())
                        .addValue("carrierId", route.getCarrier() != null ? route.getCarrier().getId() : null)
                        .addValue("id", route.getId())
        );
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM routes WHERE id = :id";
        jdbcTemplate.update(sql, new MapSqlParameterSource("id", id));
    }

    @Override
    public Long count() {
        String sql = """
                SELECT COUNT(*)
                FROM routes r
                WHERE r.deleted_at IS NULL
                """;

        return jdbcTemplate.queryForObject(sql, new MapSqlParameterSource(), Long.class);
    }
}
