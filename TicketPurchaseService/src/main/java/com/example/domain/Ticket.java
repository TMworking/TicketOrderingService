package com.example.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {

    private Long id;

    private Instant departureDateTime;

    private Integer seatNumber;

    private BigDecimal price;

    @JsonIgnore
    private Instant deletedAt;

    private Route route;

    private User user;
}
