package com.example.web.controller;

import com.example.mapper.TicketMapper;
import com.example.mapper.UserMapper;
import com.example.service.TicketService;
import com.example.service.UserService;
import com.example.web.dto.ticket.response.TicketPageResponse;
import com.example.web.dto.ticket.response.TicketResponse;
import com.example.web.dto.user.response.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "User")
public class UserController {

    private final UserMapper userMapper;
    private final UserService userService;
    private final TicketService ticketService;
    private final TicketMapper ticketMapper;

    @Operation(
            summary = "Получить пользователя по Id"
    )
    @GetMapping("/{id}")
    @PreAuthorize("#id == principal.id or hasRole('ADMIN')")
    public ResponseEntity<UserResponse> getUserById(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(userMapper.toResponse(userService.getById(id)));
    }

    @Operation(
            summary = "Получить билеты пользователя"
    )
    @GetMapping("{id}/tickets")
    @PreAuthorize("#id == principal.id or hasRole('ADMIN')")
    public ResponseEntity<TicketPageResponse> getUserTickets(
            @PathVariable("id") Long id,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size) {
        return ResponseEntity.ok().body(ticketMapper.toPageResponse(ticketService.getTicketsByUserId(id, page, size)));
    }
}
