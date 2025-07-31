package com.example.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Carrier {

    private Long id;

    private String name;

    private String phone;

    @JsonIgnore
    private Instant deletedAt;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    private List<Route> routes = new ArrayList<>();
}
