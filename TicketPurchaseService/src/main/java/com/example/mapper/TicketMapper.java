package com.example.mapper;

import com.example.domain.Ticket;
import com.example.model.Page;
import com.example.web.dto.Meta;
import com.example.web.dto.ticket.request.TicketCreateRequest;
import com.example.web.dto.ticket.request.TicketUpdateRequest;
import com.example.web.dto.ticket.response.TicketPageResponse;
import com.example.web.dto.ticket.response.TicketResponse;
import com.example.web.dto.ticket.response.TicketShortResponse;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
@DecoratedWith(TicketMapperDelegate.class)
public interface TicketMapper {

    TicketShortResponse toShortResponse(Ticket ticket);

    TicketResponse toResponse(Ticket ticket);

    Ticket toTicket(TicketCreateRequest request);

    Ticket toTicket(TicketUpdateRequest request);

    default TicketPageResponse toPageResponse(Page<Ticket> page) {
        return new TicketPageResponse(
                page.getContent().stream().map(this::toShortResponse).toList(),
                new Meta(page.getPageNumber(), page.getPageSize(), page.getTotalRecords())
        );
    }

    Ticket updateFromRequest(Long id, TicketUpdateRequest request);
}
