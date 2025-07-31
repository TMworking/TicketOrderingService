package com.example.repository.mapper;

import com.example.domain.Carrier;
import com.example.domain.Route;
import com.example.domain.Ticket;
import com.example.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
@RequiredArgsConstructor
public class TicketRowMapper implements RowMapper<Ticket> {

    private final RouteRowMapper routeRowMapper;
    private final UserRowMapper userRowMapper;

    @Override
    public Ticket mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Ticket.builder()
                .id(rs.getLong("ticket_id"))
                .departureDateTime(rs.getTimestamp("departure_date_time").toInstant())
                .seatNumber(rs.getInt("seat_number"))
                .price(rs.getBigDecimal("price"))
                .deletedAt(rs.getTimestamp("deleted_at") != null ?
                        rs.getTimestamp("deleted_at").toInstant() : null)
                .route(rs.getObject("route_id", Long.class) != null ?
                        routeRowMapper.mapRow(rs, rowNum) : null)
                .user(rs.getObject("user_id", Long.class) != null ?
                        userRowMapper.mapRow(rs, rowNum) : null)
                .build();
    }
}
