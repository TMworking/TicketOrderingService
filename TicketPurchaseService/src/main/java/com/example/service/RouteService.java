package com.example.service;

import com.example.domain.Route;
import com.example.model.Page;

public interface RouteService {

    Route getById(Long id);

    Page<Route> getRoutesPage(Integer page, Integer size);

    Route createRoute(Route route);

    void updateRoute(Route route);

    void deleteRoute(Long id);
}
