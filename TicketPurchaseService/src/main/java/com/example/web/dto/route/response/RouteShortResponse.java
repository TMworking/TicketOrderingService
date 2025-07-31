package com.example.web.dto.route.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RouteShortResponse {

    private Long id;

    private String departurePoint;

    private String destinationPoint;
}
