package com.example.web.dto.carrier.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarrierResponse {

    private Long id;

    private String name;

    private String phone;
}
