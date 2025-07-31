package com.example.mapper;

import com.example.domain.Carrier;
import com.example.service.CarrierService;
import com.example.web.dto.carrier.request.CarrierUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class CarrierMapperDelegate implements CarrierMapper {

    @Autowired
    private CarrierService carrierService;

    public Carrier updateFromRequest(Long id, CarrierUpdateRequest request) {
        Carrier carrier = carrierService.getById(id);

        if (request.getName() != null) {
            carrier.setName(request.getName());
        }
        if (request.getPhone() != null) {
            carrier.setPhone(request.getPhone());
        }

        return carrier;
    }
}
