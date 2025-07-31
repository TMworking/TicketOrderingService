package com.example.repository;

import com.example.domain.TicketPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class TicketPayloadRepositoryImpl implements TicketPayloadRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public void saveTicketPayload(TicketPayload ticketPayload) {
        String sql = """
                INSERT INTO ticket_payload (user_id, ticket_id)
                VALUES (:userId, :ticketId)
                """;

        Map<String, Object> params = new HashMap<>();
        params.put("userId", ticketPayload.getUserId());
        params.put("ticketId", ticketPayload.getTicketId());

        jdbcTemplate.update(sql, params);
    }
}
