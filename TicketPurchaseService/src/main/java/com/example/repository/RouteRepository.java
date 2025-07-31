package com.example.repository;

import com.example.domain.Route;

import java.util.List;
import java.util.Optional;

public interface RouteRepository {

    Optional<Route> findById(Long id);

    List<Route> findPage(Integer page, Integer size);

    Route save(Route route);

    void update(Route route);

    void delete(Long id);

    Long count();
}
