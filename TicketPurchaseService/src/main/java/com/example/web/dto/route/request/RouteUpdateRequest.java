package com.example.web.dto.route.request;

import com.example.annotation.AtLeastOneNotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@AtLeastOneNotEmpty
public class RouteUpdateRequest {

    private Long carrierId;

    private String departurePoint;

    private String destinationPoint;

    @Positive(message = "Duration should be positive number")
    private Integer minutesDuration;
}
