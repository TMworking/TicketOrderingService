package com.example.web.dto.carrier.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CarrierCreateRequest {

    @NotBlank(message = "Name shouldn't be empty")
    private String name;

    @NotBlank(message = "Phone shouldn't be empty")
    private String phone;
}
