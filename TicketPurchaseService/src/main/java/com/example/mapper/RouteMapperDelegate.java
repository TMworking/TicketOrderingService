package com.example.mapper;

import com.example.domain.Carrier;
import com.example.domain.Route;
import com.example.service.CarrierService;
import com.example.service.RouteService;
import com.example.web.dto.route.request.RouteCreateRequest;
import com.example.web.dto.route.request.RouteUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class RouteMapperDelegate implements RouteMapper {

    @Autowired
    private CarrierService carrierService;
    @Autowired
    private RouteService routeService;

    @Override
    public Route toRoute(RouteCreateRequest request) {
        Carrier carrier = carrierService.getById(request.getCarrierId());
        return Route.builder()
                .departurePoint(request.getDeparturePoint())
                .destinationPoint(request.getDestinationPoint())
                .minutesDuration(request.getMinutesDuration())
                .carrier(carrier)
                .build();
    }

    @Override
    public Route updateFromRequest(Long id, RouteUpdateRequest request) {
        Route route = routeService.getById(id);

        if (request.getDeparturePoint() != null) {
            route.setDeparturePoint(request.getDeparturePoint());
        }
        if (request.getDestinationPoint() != null) {
            route.setDestinationPoint(request.getDestinationPoint());
        }
        if (request.getMinutesDuration() != null) {
            route.setMinutesDuration(request.getMinutesDuration());
        }
        if (request.getCarrierId() != null) {
            Carrier carrier = carrierService.getById(request.getCarrierId());
            route.setCarrier(carrier);
        }

        return route;
    }
}
