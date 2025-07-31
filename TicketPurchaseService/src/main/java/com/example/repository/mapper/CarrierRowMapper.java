package com.example.repository.mapper;

import com.example.domain.Carrier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CarrierRowMapper implements RowMapper<Carrier> {
    @Override
    public Carrier mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Carrier.builder()
                .id(rs.getLong("carrier_id"))
                .name(rs.getString("carrier_name"))
                .phone(rs.getString("carrier_phone"))
                .build();
    }
}
