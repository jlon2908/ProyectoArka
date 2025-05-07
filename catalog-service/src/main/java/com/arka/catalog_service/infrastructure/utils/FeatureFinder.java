package com.arka.catalog_service.infrastructure.utils;

import com.arka.catalog_service.application.ports.output.FeaturePersistencePort;
import com.arka.catalog_service.application.exception.Feature.FeatureNotFoundException;
import com.arka.catalog_service.domain.model.Feature;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class FeatureFinder {
    private final FeaturePersistencePort featurePersistencePort;

    public Mono<Feature> findByName(String name) {
        return featurePersistencePort.findByName(name)
                .switchIfEmpty(Mono.error(new FeatureNotFoundException(name)));
    }
}