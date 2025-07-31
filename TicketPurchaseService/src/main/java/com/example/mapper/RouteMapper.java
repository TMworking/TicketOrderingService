package com.example.mapper;

import com.example.domain.Route;
import com.example.model.Page;
import com.example.web.dto.Meta;
import com.example.web.dto.route.request.RouteCreateRequest;
import com.example.web.dto.route.request.RouteUpdateRequest;
import com.example.web.dto.route.response.RoutePageResponse;
import com.example.web.dto.route.response.RouteResponse;
import com.example.web.dto.route.response.RouteShortResponse;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
@DecoratedWith(RouteMapperDelegate.class)
public interface RouteMapper {

    RouteResponse toResponse(Route route);

    RouteShortResponse toShortResponse(Route route);

    Route toRoute(RouteCreateRequest request);

    Route toRoute(RouteUpdateRequest request);

    default RoutePageResponse toPageResponse(Page<Route> page) {
        return new RoutePageResponse(
                page.getContent().stream().map(this::toShortResponse).toList(),
                new Meta(page.getPageNumber(), page.getPageSize(), page.getTotalRecords())
        );
    }

    Route updateFromRequest(Long id, RouteUpdateRequest request);
}
