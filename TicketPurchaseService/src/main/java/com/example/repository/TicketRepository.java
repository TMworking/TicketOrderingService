package com.example.repository;

import com.example.domain.Ticket;
import com.example.web.dto.ticket.request.TicketSearchRequest;

import java.util.List;
import java.util.Optional;

public interface TicketRepository {

    Optional<Ticket> findById(Long id);

    List<Ticket> findByUserId(Long id, Integer page, Integer size);

    List<Ticket> findPageByFilter(TicketSearchRequest request);

    Ticket save(Ticket ticket);

    void update(Ticket ticket);

    void delete(Long id);

    Long count(TicketSearchRequest request);
}
