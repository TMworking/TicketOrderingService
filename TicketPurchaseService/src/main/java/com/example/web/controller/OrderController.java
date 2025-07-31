package com.example.web.controller;

import com.example.mapper.TicketMapper;
import com.example.service.TicketService;
import com.example.web.dto.ticket.response.TicketResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
@Tag(name = "Order")
public class OrderController {

    private final TicketService ticketService;
    private final TicketMapper ticketMapper;

    @Operation(
            summary = "Заказать билет"
    )
    @PostMapping("/user/{userId}/ticket/{ticketId}")
    @PreAuthorize("#id == principal.id")
    public ResponseEntity<TicketResponse> orderTickets(@PathVariable("userId") Long userId, @PathVariable("ticketId") Long ticketId) {
        return ResponseEntity.ok().body(ticketMapper.toResponse(ticketService.orderTicket(userId, ticketId)));
    }
}
