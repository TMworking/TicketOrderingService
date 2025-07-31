package com.example.web.dto.ticket.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketOrderResponse {

    private List<TicketResponse> ticketIds;

    private BigDecimal totalPrice;
}
