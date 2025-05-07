package com.arka.catalog_service.application.ports.output;

import com.arka.catalog_service.domain.model.Brand;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BrandPersistencePort {
    Mono<Brand> save(Brand brand);
    Mono<Brand> update(Long id, Brand brand);
    Mono<Void> delete(Long id);
    Flux<Brand> findAll();
    Mono<Brand> findById(Long id);
    Mono<Boolean> existsByNombre(String nombre);
    Mono<Brand> findByName(String name);


}
