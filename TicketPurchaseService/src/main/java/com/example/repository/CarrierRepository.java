package com.example.repository;

import com.example.domain.Carrier;

import java.util.List;
import java.util.Optional;

public interface CarrierRepository {

    Optional<Carrier> findById(Long id);

    List<Carrier> findPage(Integer page, Integer size);

    Carrier save(Carrier carrier);

    void update(Carrier carrier);

    void delete(Long id);

    Long count();
}
