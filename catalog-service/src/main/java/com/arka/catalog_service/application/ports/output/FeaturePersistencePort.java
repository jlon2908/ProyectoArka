package com.arka.catalog_service.application.ports.output;

import com.arka.catalog_service.domain.model.Feature;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FeaturePersistencePort {
    Mono<Feature> save(Feature feature);
    Mono<Feature> update(Long id, Feature feature);
    Mono<Void> deleteById(Long id);
    Mono<Feature> findById(Long id);
    Flux<Feature> findAll();
    Mono<Feature> findByName(String name);

}
