package com.arka.catalog_service.application.usecase;

import com.arka.catalog_service.application.ports.input.FeatureServicePort;
import com.arka.catalog_service.application.ports.output.FeaturePersistencePort;
import com.arka.catalog_service.application.exception.Feature.DuplicateFeatureException;
import com.arka.catalog_service.application.exception.Feature.FeatureNotFoundException;
import com.arka.catalog_service.domain.model.Feature;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class FeatureServiceImpl implements FeatureServicePort {

    private  final FeaturePersistencePort featurePersistencePort;

    @Override
    public Mono<Feature> create(Feature feature) {
        return featurePersistencePort.findByName(feature.getName())
                .flatMap(existing -> Mono.<Feature>error(new DuplicateFeatureException(feature.getName())))
                .switchIfEmpty(featurePersistencePort.save(feature));    }

    @Override
    public Mono<Feature> update(Long id, Feature feature) {
        return featurePersistencePort.findById(id)
                .switchIfEmpty(Mono.error(new FeatureNotFoundException(id)))
                .flatMap(existing -> {
                    feature.setId(id);
                    return featurePersistencePort.update(id, feature);
                });    }

    @Override
    public Mono<Void> delete(Long id) {
        return featurePersistencePort.findById(id)
                .switchIfEmpty(Mono.error(new FeatureNotFoundException(id)))
                .flatMap(existing -> featurePersistencePort.deleteById(id));    }

    @Override
    public Mono<Feature> getById(Long id) {
        return featurePersistencePort.findById(id)
                .switchIfEmpty(Mono.error(new FeatureNotFoundException(id)));    }

    @Override
    public Flux<Feature> getAll() {
        return featurePersistencePort.findAll();
    }
}
