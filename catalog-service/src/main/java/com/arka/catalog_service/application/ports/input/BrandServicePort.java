package com.arka.catalog_service.application.ports.input;

import com.arka.catalog_service.domain.model.Brand;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BrandServicePort {
    Mono<Brand> create(Brand brand);
    Mono<Brand> update(Long id, Brand brand);
    Mono<Void> delete(Long id);
    Flux<Brand> getAll();
    Mono<Brand> getById(Long id);
}
