package com.example.web.dto.ticket.response;

import com.example.web.dto.Meta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketPageResponse {
    private List<TicketShortResponse> data;
    private Meta meta;
}
