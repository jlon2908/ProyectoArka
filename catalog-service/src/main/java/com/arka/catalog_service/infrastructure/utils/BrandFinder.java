package com.arka.catalog_service.infrastructure.utils;

import com.arka.catalog_service.application.ports.output.BrandPersistencePort;
import com.arka.catalog_service.application.exception.Brand.BrandNotFoundException;
import com.arka.catalog_service.domain.model.Brand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class BrandFinder {
    private final BrandPersistencePort brandPersistencePort;

    public Mono<Brand> findByName(String name) {
        return brandPersistencePort.findByName(name)
                .switchIfEmpty(Mono.error(new BrandNotFoundException(name)));
    }
}