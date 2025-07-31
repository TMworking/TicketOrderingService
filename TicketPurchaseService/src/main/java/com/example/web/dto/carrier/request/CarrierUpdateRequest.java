package com.example.web.dto.carrier.request;

import com.example.annotation.AtLeastOneNotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@AtLeastOneNotEmpty
public class CarrierUpdateRequest {

    private String name;

    private String phone;
}
