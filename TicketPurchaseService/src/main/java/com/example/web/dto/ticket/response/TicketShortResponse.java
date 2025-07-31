package com.example.web.dto.ticket.response;

import com.example.web.dto.route.response.RouteShortResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketShortResponse {

    private Long id;

    private RouteShortResponse route;

    private Instant departureDateTime;

    private Integer seatNumber;

    private BigDecimal price;
}
