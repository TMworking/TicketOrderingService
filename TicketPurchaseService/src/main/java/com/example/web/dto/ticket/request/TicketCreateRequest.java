package com.example.web.dto.ticket.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketCreateRequest {

    @NotNull(message = "Route id shouldn't be null")
    private Long routeId;

    @NotNull(message = "Departure date shouldn't be null")
    private Instant departureDateTime;

    @Positive(message = "Seat number should be positive")
    private Integer seatNumber;

    @NotNull(message = "Price shouldn't be null")
    @Positive(message = "Price should be positive")
    private BigDecimal price;
}
