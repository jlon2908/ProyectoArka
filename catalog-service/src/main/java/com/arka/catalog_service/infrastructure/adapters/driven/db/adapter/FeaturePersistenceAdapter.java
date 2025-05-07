package com.arka.catalog_service.infrastructure.adapters.driven.db.adapter;

import com.arka.catalog_service.application.ports.output.FeaturePersistencePort;
import com.arka.catalog_service.domain.model.Feature;
import com.arka.catalog_service.infrastructure.adapters.driven.db.Mapper.FeaturePersistenceMapper;
import com.arka.catalog_service.infrastructure.adapters.driven.db.repository.FeatureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class FeaturePersistenceAdapter implements FeaturePersistencePort {
    private  final FeatureRepository repository;
    private  final FeaturePersistenceMapper mapper;
    @Override
    public Mono<Feature> save(Feature feature) {
        return repository.save(mapper.toEntity(feature))
                .map(mapper::toDomain);    }

    @Override
    public Mono<Feature> update(Long id, Feature feature) {
        return repository.findById(id)
                .flatMap(existing -> {
                    existing.setName(feature.getName());
                    return repository.save(existing);
                })
                .map(mapper::toDomain);    }

    @Override
    public Mono<Void> deleteById(Long id) {
        return repository.deleteById(id);
    }

    @Override
    public Mono<Feature> findById(Long id) {
        return repository.findById(id)
                .map(mapper::toDomain);    }

    @Override
    public Flux<Feature> findAll() {
        return repository.findAll()
                .map(mapper::toDomain);    }

    @Override
    public Mono<Feature> findByName(String name) {
        return repository.findByName(name)
                .map(mapper::toDomain);    }
}
