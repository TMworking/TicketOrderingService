package com.example.repository.impl;

import com.example.domain.Ticket;
import com.example.model.SortOption;
import com.example.repository.TicketRepository;
import com.example.repository.mapper.TicketRowMapper;
import com.example.web.dto.ticket.request.TicketSearchRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TicketRepositoryImpl implements TicketRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final TicketRowMapper ticketRowMapper;

    @Override
    public Optional<Ticket> findById(Long id) {
        String sql = """
                SELECT
                    t.id as ticket_id,
                    t.departure_date_time,
                    t.seat_number,
                    t.price,
                    t.deleted_at,
                    r.id as route_id,
                    r.departure_point,
                    r.destination_point,
                    r.minutes_duration,
                    c.id as carrier_id,
                    c.name as carrier_name,
                    c.phone as carrier_phone,
                    u.id as user_id,
                    u.name as user_name,
                    u.surname as user_surname,
                    u.patronymic as user_patronymic,
                    u.login as user_login,
                    u.password as user_password,
                    rl.id as role_id,
                    rl.role_name
                FROM tickets t
                JOIN routes r ON t.route_id = r.id
                LEFT JOIN carriers c ON r.carrier_id = c.id
                LEFT JOIN users u ON t.user_id = u.id
                LEFT JOIN roles rl ON u.role_id = rl.id
                WHERE t.id = :id AND t.deleted_at IS NULL
                """;
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(
                            sql,
                            new MapSqlParameterSource("id", id),
                            ticketRowMapper
                    )
            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Ticket> findByUserId(Long id, Integer page, Integer size) {
        MapSqlParameterSource params = new MapSqlParameterSource();

        String sql = """
                SELECT
                    t.id as ticket_id,
                    t.departure_date_time,
                    t.seat_number,
                    t.price,
                    t.deleted_at,
                    r.id as route_id,
                    r.departure_point,
                    r.destination_point,
                    r.minutes_duration,
                    c.id as carrier_id,
                    c.name as carrier_name,
                    c.phone as carrier_phone,
                    u.id as user_id,
                    u.name as user_name,
                    u.surname as user_surname,
                    u.patronymic as user_patronymic,
                    u.login as user_login,
                    u.password as user_password,
                    rl.id as role_id,
                    rl.role_name
                FROM tickets t
                JOIN routes r ON t.route_id = r.id
                LEFT JOIN carriers c ON r.carrier_id = c.id
                LEFT JOIN users u ON t.user_id = u.id
                LEFT JOIN roles rl ON u.role_id = rl.id
                WHERE u.id = :id AND t.deleted_at IS NULL
                LIMIT :size OFFSET :offset
                """;

        params.addValue("id", id);
        params.addValue("size", size);
        params.addValue("offset", page * size);

        return jdbcTemplate.query(
                sql,
                params,
                ticketRowMapper
        );
    }

    @Override
    public List<Ticket> findPageByFilter(TicketSearchRequest request) {
        StringBuilder sql = new StringBuilder("""
            SELECT
                t.id as ticket_id,
                t.departure_date_time,
                t.seat_number,
                t.price,
                t.deleted_at,
                r.id as route_id,
                r.departure_point,
                r.destination_point,
                r.minutes_duration,
                c.id as carrier_id,
                c.name as carrier_name,
                c.phone as carrier_phone,
                u.id as user_id,
                u.name as user_name,
                u.surname as user_surname,
                u.patronymic as user_patronymic,
                u.login as user_login,
                u.password as user_password,
                rl.id as role_id,
                rl.role_name
            FROM tickets t
            JOIN routes r ON t.route_id = r.id
            LEFT JOIN carriers c ON r.carrier_id = c.id
            LEFT JOIN users u ON t.user_id = u.id
            LEFT JOIN roles rl ON u.role_id = rl.id
            WHERE t.deleted_at IS NULL
            """);

        MapSqlParameterSource params = new MapSqlParameterSource();

        addFilter(sql, params,
                "t.departure_date_time >= :from", "from", request.getDepartureDateTimeFrom());
        addFilter(sql, params,
                "t.departure_date_time <= :to", "to", request.getDepartureDateTimeTo());
        if (request.getDeparturePoint() != null) {
            addFilter(sql, params,
                    "r.departure_point ILIKE :departurePoint", "departurePoint",
                    "%" + request.getDeparturePoint() + "%");
        }
        if (request.getDestinationPoint() != null) {
            addFilter(sql, params,
                    "r.destination_point ILIKE :destinationPoint", "destinationPoint",
                    "%" + request.getDestinationPoint() + "%");
        }

        if (request.getSortOptionList() != null && !request.getSortOptionList().isEmpty()) {
            sql.append(" ORDER BY ");
            for (SortOption sort : request.getSortOptionList()) {
                sql.append("t.").append(sort.getField())
                        .append(" ").append(sort.getDirection())
                        .append(", ");
            }
            sql.delete(sql.length() - 2, sql.length());
        }

        sql.append(" LIMIT :size OFFSET :offset");
        params.addValue("size", request.getSize());
        params.addValue("offset", request.getPage() * request.getSize());

        return jdbcTemplate.query(sql.toString(), params, ticketRowMapper);
    }

    @Override
    public Ticket save(Ticket ticket) {
        String sql = """
            INSERT INTO tickets (departure_date_time, seat_number, price, route_id, user_id)
            VALUES (:departureDateTime, :seatNumber, :price, :routeId, :userId)
            RETURNING id
            """;

        OffsetDateTime departureDateTime = ticket.getDepartureDateTime() != null
                ? ticket.getDepartureDateTime().atOffset(ZoneOffset.UTC) : null;
        Long id = jdbcTemplate.queryForObject(
                sql,
                new MapSqlParameterSource()
                        .addValue("departureDateTime", departureDateTime)
                        .addValue("seatNumber", ticket.getSeatNumber())
                        .addValue("price", ticket.getPrice())
                        .addValue("routeId", ticket.getRoute().getId())
                        .addValue("userId", ticket.getUser() != null ? ticket.getUser().getId() : null),
                Long.class
        );

        ticket.setId(id);
        return ticket;
    }

    @Override
    public void update(Ticket ticket) {
        String sql = """
            UPDATE tickets SET
                departure_date_time = :departureDateTime,
                seat_number = :seatNumber,
                price = :price,
                route_id = :routeId,
                user_id = :userId
            WHERE id = :id
            """;

        OffsetDateTime departureDateTime = ticket.getDepartureDateTime() != null
                ? ticket.getDepartureDateTime().atOffset(ZoneOffset.UTC) : null;
        jdbcTemplate.update(
                sql,
                new MapSqlParameterSource()
                        .addValue("departureDateTime", departureDateTime)
                        .addValue("seatNumber", ticket.getSeatNumber())
                        .addValue("price", ticket.getPrice())
                        .addValue("routeId", ticket.getRoute().getId())
                        .addValue("userId", ticket.getUser() != null ? ticket.getUser().getId() : null)
                        .addValue("id", ticket.getId())
        );
    }

    @Override
    public void delete(Long id) {
        String sql = """
                UPDATE tickets SET
                    deleted_at = NOW()
                WHERE id = :id""";
        jdbcTemplate.update(sql, new MapSqlParameterSource("id", id));
    }

    @Override
    public Long count(TicketSearchRequest request) {
        StringBuilder sql = new StringBuilder("""
            SELECT COUNT(*)
            FROM tickets t
            JOIN routes r ON t.route_id = r.id
            LEFT JOIN users u ON t.user_id = u.id
            WHERE t.deleted_at IS NULL
            """);

        MapSqlParameterSource params = new MapSqlParameterSource();

        addFilter(sql, params,
                "t.departure_date_time >= :from", "from", request.getDepartureDateTimeFrom());
        addFilter(sql, params,
                "t.departure_date_time <= :to", "to", request.getDepartureDateTimeTo());
        if (request.getDeparturePoint() != null) {
            addFilter(sql, params,
                    "r.departure_point ILIKE :departurePoint", "departurePoint",
                    "%" + request.getDeparturePoint() + "%");
        }
        if (request.getDestinationPoint() != null) {
            addFilter(sql, params,
                    "r.destination_point ILIKE :destinationPoint", "destinationPoint",
                    "%" + request.getDestinationPoint() + "%");
        }

        return jdbcTemplate.queryForObject(sql.toString(), params, Long.class);
    }

    private void addFilter(StringBuilder sql, MapSqlParameterSource params, String condition, String paramName, Object value) {
        if (value != null) {
            sql.append(" AND ").append(condition);
            params.addValue(paramName, value);
        }
    }
}
