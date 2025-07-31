package com.example.web.controller;

import com.example.mapper.RouteMapper;
import com.example.service.RouteService;
import com.example.web.dto.route.request.RouteCreateRequest;
import com.example.web.dto.route.request.RouteUpdateRequest;
import com.example.web.dto.route.response.RoutePageResponse;
import com.example.web.dto.route.response.RouteResponse;
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
@RequestMapping("/api/v1/routes")
@RequiredArgsConstructor
@Tag(name = "Route")
public class RouteController {

    private final RouteMapper routeMapper;
    private final RouteService routeService;

    @Operation(
            summary = "Получить маршрут по Id"
    )
    @GetMapping("/{id}")
    public ResponseEntity<RouteResponse> getRouteById(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(routeMapper.toResponse(routeService.getById(id)));
    }

    @Operation(
            summary = "Получить список маршрутов"
    )
    @GetMapping
    public ResponseEntity<RoutePageResponse> getAllRoutes(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size) {
        return ResponseEntity.ok().body(routeMapper.toPageResponse(routeService.getRoutesPage(page, size)));
    }

    @Operation(
            summary = "Создать маршрут"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<RouteResponse> createRoute(@Valid @RequestBody RouteCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(routeMapper.toResponse(routeService.createRoute(routeMapper.toRoute(request))));
    }

    @Operation(
            summary = "Обновить маршрут"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateRoute(@PathVariable("id") Long id, @Valid @RequestBody RouteUpdateRequest request) {
        routeService.updateRoute(routeMapper.updateFromRequest(id, request));
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Удалить маршрут"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoute(@PathVariable("id") Long id) {
        routeService.deleteRoute(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
