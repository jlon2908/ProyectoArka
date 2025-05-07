package com.arka.catalog_service.infrastructure.adapters.driven.db.repository;

import com.arka.catalog_service.infrastructure.adapters.driven.db.Entity.FeatureEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface FeatureRepository extends ReactiveCrudRepository<FeatureEntity, Long> {
    Mono<FeatureEntity> findByName(String name);
}
