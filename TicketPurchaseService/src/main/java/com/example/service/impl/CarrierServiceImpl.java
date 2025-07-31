package com.example.service.impl;

import com.example.domain.Carrier;
import com.example.exception.EntityNotFoundException;
import com.example.model.Page;
import com.example.repository.CarrierRepository;
import com.example.service.CarrierService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CarrierServiceImpl implements CarrierService {

    private final CarrierRepository carrierRepository;

    @Override
    public Carrier getById(Long id) {
        log.debug("Searching carrier by ID: {}", id);
        Carrier carrier = carrierRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Carrier with id {} not found", id);
                    return new EntityNotFoundException(
                            MessageFormat.format("Carrier with id {0} not found", id)
                    );
                });
        log.debug("Found carrier: ID = {}", id);
        return carrier;
    }

    @Override
    public Page<Carrier> getCarriersPage(Integer page, Integer size) {
        log.debug("Fetching carriers page");
        List<Carrier> carriers = carrierRepository.findPage(page, size);
        Long count = carrierRepository.count();
        Page<Carrier> resPage = new Page<>(carriers, page, size, count);
        log.debug("Retrieved {} carriers (page {})", resPage.getContent().size(), resPage.getPageNumber());
        return resPage;
    }

    @Override
    @Transactional
    public Carrier createCarrier(Carrier carrier) {
        log.debug("Creating new carrier: name={}", carrier.getName());
        Carrier savedCarrier = carrierRepository.save(carrier);
        log.debug("Carrier created successfully: {}", carrier.getId());
        return savedCarrier;
    }

    @Override
    @Transactional
    public void updateCarrier(Carrier carrier) {
        log.debug("Updating carrier: ID={}", carrier.getId());
        carrierRepository.update(carrier);
        log.debug("Carrier updated successfully: {}", carrier);
    }

    @Override
    @Transactional
    public void deleteCarrier(Long id) {
        log.debug("Deleting carrier: ID={}", id);
        carrierRepository.delete(id);
        log.debug("Carrier {} deleted", id);
    }
}
