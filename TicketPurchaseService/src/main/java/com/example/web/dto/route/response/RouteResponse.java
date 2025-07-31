package com.example.web.dto.route.response;

import com.example.web.dto.carrier.response.CarrierShortResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RouteResponse {

    private Long id;

    private String departurePoint;

    private String destinationPoint;

    private Integer minutesDuration;

    private CarrierShortResponse carrier;
}
