package com.example.web.dto.ticket.response;

import com.example.web.dto.route.response.RouteResponse;
import com.example.web.dto.user.response.UserNameResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketResponse {

    private Long id;

    private RouteResponse route;

    private Instant departureDateTime;

    private Integer seatNumber;

    private BigDecimal price;

    private UserNameResponse user;
}
