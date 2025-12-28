package com.store.application.mapper;

import com.store.application.dto.SaleDto;
import com.store.application.model.sale.Sale;

public class SaleMapper {

    public static SaleDto toDto(Sale entity) {
        if (entity == null) return null;
        return SaleDto.builder()
                .id(entity.getId())
                .product(entity.getProduct())
                .amount(entity.getAmount())
                .build();
    }

    public static Sale toEntity(SaleDto dto) {
        if (dto == null) return null;
        return new Sale(
                dto.getId(),
                dto.getProduct(),
                dto.getAmount()
        );
    }
}
