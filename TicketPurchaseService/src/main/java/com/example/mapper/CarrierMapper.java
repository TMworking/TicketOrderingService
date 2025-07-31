package com.example.mapper;

import com.example.domain.Carrier;
import com.example.model.Page;
import com.example.web.dto.Meta;
import com.example.web.dto.carrier.request.CarrierCreateRequest;
import com.example.web.dto.carrier.request.CarrierUpdateRequest;
import com.example.web.dto.carrier.response.CarrierPageResponse;
import com.example.web.dto.carrier.response.CarrierResponse;
import com.example.web.dto.carrier.response.CarrierShortResponse;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
@DecoratedWith(CarrierMapperDelegate.class)
public interface CarrierMapper {

    CarrierResponse toResponse(Carrier carrier);

    CarrierShortResponse toShortResponse(Carrier carrier);

    Carrier toCarrier(CarrierCreateRequest request);

    Carrier toCarrier(CarrierUpdateRequest request);

    default CarrierPageResponse toPageResponse(Page<Carrier> page) {
        return new CarrierPageResponse(
                page.getContent().stream().map(this::toShortResponse).toList(),
                new Meta(page.getPageNumber(), page.getPageSize(), page.getTotalRecords())
        );
    }

    Carrier updateFromRequest(Long id, CarrierUpdateRequest request);
}
