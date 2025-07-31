package com.example.service.impl;

import com.example.domain.Route;
import com.example.exception.EntityNotFoundException;
import com.example.model.Page;
import com.example.repository.RouteRepository;
import com.example.service.RouteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RouteServiceImpl implements RouteService {

    private final RouteRepository routeRepository;

    @Override
    public Route getById(Long id) {
        log.debug("Searching route by ID: {}", id);
        Route route = routeRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Route with id {} not found", id);
                    return new EntityNotFoundException(
                            MessageFormat.format("Route with id {0} not found", id)
                    );
                });
        log.debug("Found route: ID = {}", id);
        return route;
    }

    @Override
    public Page<Route> getRoutesPage(Integer page, Integer size) {
        log.debug("Fetching routes page");
        List<Route> routes = routeRepository.findPage(page, size);
        Long count = routeRepository.count();
        Page<Route> resPage = new Page<>(routes, page, size, count);
        log.debug("Retrieved {} routes (page {})", resPage.getContent().size(), resPage.getPageNumber());
        return resPage;
    }

    @Override
    @Transactional
    public Route createRoute(Route route) {
        log.debug("Creating new route: destinationPoint={}, departurePoint={}", route.getDestinationPoint(), route.getDeparturePoint());
        Route savedRoute = routeRepository.save(route);
        log.debug("Route created successfully: {}", route.getId());
        return savedRoute;
    }

    @Override
    @Transactional
    public void updateRoute(Route route) {
        log.debug("Updating route: ID={}", route.getId());
        routeRepository.update(route);
        log.debug("Route updated successfully: {}", route);
    }

    @Override
    @Transactional
    public void deleteRoute(Long id) {
        log.debug("Deleting route: ID={}", id);
        routeRepository.delete(id);
        log.debug("Route {} deleted", id);
    }
}
