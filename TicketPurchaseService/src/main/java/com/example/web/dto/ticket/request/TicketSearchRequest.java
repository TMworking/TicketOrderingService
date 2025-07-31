package com.example.web.dto.ticket.request;

import com.example.model.SortOption;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketSearchRequest {
    @Builder.Default
    private Integer page = 0;
    @Builder.Default
    private Integer size = 10;
    @Builder.Default
    private List<SortOption> sortOptionList = List.of(new SortOption("id", "asc"));

    private Instant departureDateTimeFrom;
    private Instant departureDateTimeTo;

    private String departurePoint;
    private String destinationPoint;

    private String carrierName;
}
