package com.example.service.impl;

import com.example.domain.Ticket;
import com.example.domain.TicketPayload;
import com.example.domain.User;
import com.example.exception.EntityNotFoundException;
import com.example.exception.OrderTicketException;
import com.example.exception.TicketUpdateException;
import com.example.model.Page;
import com.example.repository.TicketRepository;
import com.example.service.TicketService;
import com.example.service.UserService;
import com.example.web.dto.ticket.request.TicketSearchRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TicketServiceImpl implements TicketService {

    private static final String TOPIC = "ticket-purchases";
    private final TicketRepository ticketRepository;
    private final UserService userService;
    private final KafkaTemplate<String, TicketPayload> kafkaTemplate;

    @Override
    public Ticket getById(Long id) {
        log.debug("Searching ticket by ID: {}", id);
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Ticket with id {} not found", id);
                    return new EntityNotFoundException(
                            MessageFormat.format("Ticket with id {0} not found", id)
                    );
                });
        log.debug("Found ticket: ID = {}", id);
        return ticket;
    }

    @Override
    @Cacheable(value = "userTickets", key = "#id + '_' + #page + '_' + #size")
    public Page<Ticket> getTicketsByUserId(Long id, Integer page, Integer size) {
        log.debug("Searching tickets by user ID: {}", id);
        List<Ticket> tickets = ticketRepository.findByUserId(id, page, size);
        long count = tickets.size();
        Page<Ticket> resPage = new Page<>(tickets, page, size, count);
        log.debug("Found {} tickets", tickets.size());
        return resPage;
    }

    @Override
    public Page<Ticket> getTicketsWithFilter(TicketSearchRequest request) {
        log.debug("Fetching ticket page with filter: {}", request);
        List<Ticket> tickets = ticketRepository.findPageByFilter(request);
        Long count = ticketRepository.count(request);
        Page<Ticket> page = new Page<>(tickets, request.getPage(), request.getSize(), count);
        log.debug("Retrieved {} tickets (page {})", page.getContent().size(), page.getPageNumber());
        return page;
    }

    @Override
    @Transactional
    public Ticket createTicket(Ticket ticket) {
        log.debug("Creating new ticket: seatNumber={}, route={}", ticket.getSeatNumber(), ticket.getRoute());
        Ticket savedTicket = ticketRepository.save(ticket);
        log.debug("Ticket created successfully: {}", savedTicket.getId());
        return savedTicket;
    }

    @Override
    @Transactional
    public void updateTicket(Ticket ticket) {
        log.debug("Updating ticket: ID={}", ticket.getId());
        Ticket currentTicket = getById(ticket.getId());
        if (currentTicket.getUser() != null) {
            log.error("Ticket {} have active user, can't update ticket", ticket.getId());
            throw new TicketUpdateException("Can't update ticket data with active user");
        }
        ticketRepository.update(ticket);
        log.debug("Ticket updated successfully: {}", ticket);
    }

    @Override
    @Transactional
    public void deleteTicket(Long id) {
        log.debug("Deleting ticket: ID={}", id);
        Ticket ticket = getById(id);

        Instant destinationTime = ticket.getDepartureDateTime().plus(ticket.getRoute().getMinutesDuration(), ChronoUnit.MINUTES);
        if (ticket.getUser() != null && destinationTime.isAfter(Instant.now())) {
            log.error("Ticket {} have active route, can't delete ticket", ticket.getId());
            throw new TicketUpdateException("Can't delete ticket with active user and not ended route");
        }
        log.debug("Ticket {} deleted at: {}", ticket.getId(), ticket.getDeletedAt());
        ticketRepository.delete(id);
    }

    @Override
    @Transactional
    @CacheEvict(value = "userTickets", key = "#userId + '*'", allEntries = true)
    public Ticket orderTicket(Long userId, Long ticketId) {
        User user = userService.getById(userId);
        Ticket ticket = getById(ticketId);
        log.debug("User with ID={} is buying ticket with ID={}", userId, ticketId);

        if (ticket.getUser() != null) {
            log.error("Ticket with id {} already bought", ticketId);
            throw new OrderTicketException(
                    MessageFormat.format("Ticket with id {0} already bought", ticketId)
            );
        }
        ticket.setUser(user);
        ticketRepository.update(ticket);
        kafkaTemplate.send(TOPIC, new TicketPayload(userId, ticketId));
        return ticket;
    }
}
