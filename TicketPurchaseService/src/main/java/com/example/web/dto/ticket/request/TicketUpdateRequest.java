package com.example.web.dto.ticket.request;

import com.example.annotation.AtLeastOneNotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@AtLeastOneNotEmpty
public class TicketUpdateRequest {

    @Positive(message = "Route id should be positive")
    private Long routeId;

    @Positive(message = "Seat number should be positive")
    private Integer seatNumber;

    @Positive(message = "Price should be positive")
    private BigDecimal price;
}
