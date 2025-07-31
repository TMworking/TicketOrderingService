package com.example.web.controller;

import com.example.mapper.CarrierMapper;
import com.example.service.CarrierService;
import com.example.web.dto.carrier.request.CarrierCreateRequest;
import com.example.web.dto.carrier.request.CarrierUpdateRequest;
import com.example.web.dto.carrier.response.CarrierPageResponse;
import com.example.web.dto.carrier.response.CarrierResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/carriers")
@RequiredArgsConstructor
@Tag(name = "Carrier")
public class CarrierController {

    private final CarrierService carrierService;
    private final CarrierMapper carrierMapper;

    @Operation(
            summary = "Получить перевозчика по Id"
    )
    @GetMapping("/{id}")
    public ResponseEntity<CarrierResponse> getCarrierById(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(carrierMapper.toResponse(carrierService.getById(id)));
    }

    @Operation(
            summary = "Получить список перевозчиков"
    )
    @GetMapping
    public ResponseEntity<CarrierPageResponse> getAllCarriers(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size) {
        return ResponseEntity.ok().body(carrierMapper.toPageResponse(carrierService.getCarriersPage(page, size)));
    }

    @Operation(
            summary = "Создать нового перевозчика"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<CarrierResponse> createCarrier(@Valid @RequestBody CarrierCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(carrierMapper.toResponse(carrierService.createCarrier(carrierMapper.toCarrier(request))));
    }

    @Operation(
            summary = "Обновить данные перевозчика"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<CarrierResponse> updateCarrier(@PathVariable("id") Long id, @Valid @RequestBody CarrierUpdateRequest request) {
        carrierService.updateCarrier(carrierMapper.updateFromRequest(id, request));
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Удалить перевозчика"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCarrier(@PathVariable("id") Long id) {
        carrierService.deleteCarrier(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
