package com.example.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Route {

    private Long id;

    private String departurePoint;

    private String destinationPoint;

    private Integer minutesDuration;

    @JsonIgnore
    private Instant deletedAt;

    private Carrier carrier;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    private Set<Ticket> tickets = new HashSet<>();
}
