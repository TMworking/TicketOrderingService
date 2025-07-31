package com.example.mapper;

import com.example.domain.Route;
import com.example.domain.Ticket;
import com.example.service.RouteService;
import com.example.service.TicketService;
import com.example.web.dto.ticket.request.TicketCreateRequest;
import com.example.web.dto.ticket.request.TicketUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class TicketMapperDelegate implements TicketMapper {

    @Autowired
    private RouteService routeService;
    @Autowired
    private TicketService ticketService;

    @Override
    public Ticket toTicket(TicketCreateRequest request) {
        Route route = routeService.getById(request.getRouteId());
        return Ticket.builder()
                .departureDateTime(request.getDepartureDateTime())
                .seatNumber(request.getSeatNumber())
                .price(request.getPrice())
                .route(route)
                .build();
    }

    @Override
    public Ticket updateFromRequest(Long id, TicketUpdateRequest request) {
        Ticket ticket = ticketService.getById(id);

        if (request.getPrice() != null) {
            ticket.setPrice(request.getPrice());
        }
        if (request.getSeatNumber() !=null) {
            ticket.setSeatNumber(request.getSeatNumber());
        }
        if (request.getRouteId() != null) {
            Route route = routeService.getById(request.getRouteId());
            ticket.setRoute(route);
        }

        return ticket;
    }
}
