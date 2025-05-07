package com.arka.catalog_service.infrastructure.adapters.driven.db.repository;

import com.arka.catalog_service.infrastructure.adapters.driven.db.Entity.BrandEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface BrandRepository extends ReactiveCrudRepository<BrandEntity, Long> {
    Mono<Boolean> existsByNombre(String nombre);
    Mono<BrandEntity> findByNombre(String name);

}
