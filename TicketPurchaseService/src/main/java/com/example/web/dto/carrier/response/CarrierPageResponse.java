package com.example.web.dto.carrier.response;

import com.example.web.dto.Meta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarrierPageResponse {
    private List<CarrierShortResponse> data;
    private Meta meta;
}
