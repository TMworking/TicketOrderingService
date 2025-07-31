package com.example.web.dto.route.request;

import com.example.domain.Carrier;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RouteCreateRequest {

    @NotBlank(message = "Departure point shouldn't be empty")
    private String departurePoint;

    @NotBlank(message = "Destination shouldn't be empty")
    private String destinationPoint;

    @NotNull(message = "Duration shouldn't be null")
    @Positive(message = "Duration should be positive number")
    private Integer minutesDuration;

    @NotNull(message = "Carrier id shouldn't be null")
    private Long carrierId;
}
