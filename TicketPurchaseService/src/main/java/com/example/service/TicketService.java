package com.example.service;

import com.example.domain.Ticket;
import com.example.model.Page;
import com.example.web.dto.ticket.request.TicketSearchRequest;

public interface TicketService {

    Ticket getById(Long id);

    Page<Ticket> getTicketsByUserId(Long id, Integer page, Integer size);

    Page<Ticket> getTicketsWithFilter(TicketSearchRequest request);

    Ticket createTicket(Ticket ticket);

    void updateTicket(Ticket ticket);

    void deleteTicket(Long id);

    Ticket orderTicket(Long userId, Long ticketId);
}
