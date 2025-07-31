package com.example.service;

import com.example.domain.Carrier;
import com.example.model.Page;

public interface CarrierService {

    Carrier getById(Long id);

    Page<Carrier> getCarriersPage(Integer page, Integer size);

    Carrier createCarrier(Carrier carrier);

    void updateCarrier(Carrier carrier);

    void deleteCarrier(Long id);
}
