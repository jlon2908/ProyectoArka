package com.arka.catalog_service.application.ports.input;

import com.arka.catalog_service.domain.model.Feature;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FeatureServicePort {
    Mono<Feature> create(Feature feature);
    Mono<Feature> update(Long id, Feature feature);
    Mono<Void> delete(Long id);
    Mono<Feature> getById(Long id);
    Flux<Feature> getAll();

}
