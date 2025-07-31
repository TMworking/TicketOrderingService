package com.example.web.dto.route.response;

import com.example.web.dto.Meta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoutePageResponse {
    private List<RouteShortResponse> data;
    private Meta meta;
}
