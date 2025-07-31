package com.example.web.controller;

import com.example.mapper.TicketMapper;
import com.example.service.TicketService;
import com.example.web.dto.ticket.request.TicketCreateRequest;
import com.example.web.dto.ticket.request.TicketSearchRequest;
import com.example.web.dto.ticket.request.TicketUpdateRequest;
import com.example.web.dto.ticket.response.TicketPageResponse;
import com.example.web.dto.ticket.response.TicketResponse;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tickets")
@RequiredArgsConstructor
@Tag(name = "Ticket")
public class TicketController {

    private final TicketMapper ticketMapper;
    private final TicketService ticketService;

    @Operation(
            summary = "Получить билет по Id"
    )
    @GetMapping("/{id}")
    public ResponseEntity<TicketResponse> getTicketById(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(ticketMapper.toResponse(ticketService.getById(id)));
    }

    @Operation(
            summary = "Получить список билетов"
    )
    @PostMapping("/search")
    public ResponseEntity<TicketPageResponse> getAllTickets(@Valid @RequestBody TicketSearchRequest request) {
        return ResponseEntity.ok().body(ticketMapper.toPageResponse(ticketService.getTicketsWithFilter(request)));
    }

    @Operation(
            summary = "Создать билет"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<TicketResponse> createTicket(@Valid @RequestBody TicketCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ticketMapper.toResponse(ticketService.createTicket(ticketMapper.toTicket(request))));
    }

    @Operation(
            summary = "Обновить билет"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateTicket(@PathVariable("id") Long id, @Valid @RequestBody TicketUpdateRequest request) {
        ticketService.updateTicket(ticketMapper.updateFromRequest(id, request));
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Удалить билет"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable("id") Long id) {
        ticketService.deleteTicket(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
