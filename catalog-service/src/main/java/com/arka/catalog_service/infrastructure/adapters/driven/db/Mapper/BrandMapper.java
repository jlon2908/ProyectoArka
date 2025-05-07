package com.arka.catalog_service.infrastructure.adapters.driven.db.Mapper;

import com.arka.catalog_service.domain.model.Brand;
import com.arka.catalog_service.infrastructure.adapters.driven.db.Entity.BrandEntity;

public class BrandMapper {

    public static Brand toDomain(BrandEntity entity) {
        return new Brand(entity.getId(), entity.getNombre());
    }

    public static BrandEntity toEntity(Brand domain) {
        return BrandEntity.builder()
                .id(domain.getId())
                .nombre(domain.getNombre())
                .build();
    }
}
