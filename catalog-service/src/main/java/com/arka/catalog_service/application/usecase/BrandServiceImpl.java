package com.arka.catalog_service.application.usecase;

import com.arka.catalog_service.application.ports.input.BrandServicePort;
import com.arka.catalog_service.application.ports.output.BrandPersistencePort;
import com.arka.catalog_service.application.exception.Brand.BrandNotFoundException;
import com.arka.catalog_service.application.exception.Brand.DuplicateBrandException;
import com.arka.catalog_service.application.exception.NoDataFoundException;
import com.arka.catalog_service.domain.model.Brand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandServicePort {

    private final BrandPersistencePort persistencePort;


    @Override
    public Mono<Brand> create(Brand brand) {
        return persistencePort.existsByNombre(brand.getNombre())
                .flatMap(exists -> {
                    if (Boolean.TRUE.equals(exists)) {
                        return Mono.error(new DuplicateBrandException(brand.getNombre()));
                    }
                    return persistencePort.save(brand);
                });
    }

    @Override
    public Mono<Brand> update(Long id, Brand brand) {
        return persistencePort.update(id, brand);
    }

    @Override
    public Mono<Void> delete(Long id) {
        return persistencePort.delete(id);
    }

    @Override
    public Flux<Brand> getAll() {
        return persistencePort.findAll()
                .switchIfEmpty(Mono.error(new NoDataFoundException("No brands found.")));    }

    @Override
    public Mono<Brand> getById(Long id) {
        return persistencePort.findById(id)
                .switchIfEmpty(Mono.error(new BrandNotFoundException(id)));    }
}