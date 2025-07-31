package com.example.repository.mapper;

import com.example.domain.Route;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
@RequiredArgsConstructor
public class RouteRowMapper implements RowMapper<Route> {

    private final CarrierRowMapper carrierRowMapper;

    @Override
    public Route mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Route.builder()
                .id(rs.getLong("route_id"))
                .departurePoint(rs.getString("departure_point"))
                .destinationPoint(rs.getString("destination_point"))
                .minutesDuration(rs.getInt("minutes_duration"))
                .carrier(rs.getObject("carrier_id", Long.class) != null ?
                        carrierRowMapper.mapRow(rs, rowNum) : null)
                .build();
    }
}
