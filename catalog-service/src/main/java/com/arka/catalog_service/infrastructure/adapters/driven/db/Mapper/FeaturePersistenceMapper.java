package com.arka.catalog_service.infrastructure.adapters.driven.db.Mapper;

import com.arka.catalog_service.domain.model.Feature;
import com.arka.catalog_service.infrastructure.adapters.driven.db.Entity.FeatureEntity;
import org.springframework.stereotype.Component;

@Component
public class FeaturePersistenceMapper {
    public FeatureEntity toEntity(Feature feature) {
        return FeatureEntity.builder()
                .id(feature.getId())
                .name(feature.getName())
                .build();
    }

    public Feature toDomain(FeatureEntity entity) {
        return new Feature(entity.getId(), entity.getName());

    }
}
