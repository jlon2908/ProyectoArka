package com.arka.catalog_service.infrastructure.adapters.driven.db.adapter;

import com.arka.catalog_service.application.ports.output.BrandPersistencePort;
import com.arka.catalog_service.domain.model.Brand;
import com.arka.catalog_service.infrastructure.adapters.driven.db.Entity.BrandEntity;
import com.arka.catalog_service.infrastructure.adapters.driven.db.Mapper.BrandMapper;
import com.arka.catalog_service.infrastructure.adapters.driven.db.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class BrandPersistenceAdapter implements BrandPersistencePort {

    private final BrandRepository brandRepository;


    @Override
    public Mono<Brand> save(Brand brand) {
        BrandEntity entity = BrandMapper.toEntity(brand);
        return brandRepository.save(entity)
                .map(BrandMapper::toDomain);    }

    @Override
    public Mono<Brand> update(Long id, Brand brand) {
        return brandRepository.findById(id)
                .flatMap(existing -> {
                    existing.setNombre(brand.getNombre());
                    return brandRepository.save(existing);
                })
                .map(BrandMapper::toDomain);    }

    @Override
    public Mono<Void> delete(Long id) {
        return brandRepository.deleteById(id);
    }

    @Override
    public Flux<Brand> findAll() {
        return brandRepository.findAll()
                .map(BrandMapper::toDomain);    }

    @Override
    public Mono<Brand> findById(Long id) {
        return brandRepository.findById(id)
                .map(BrandMapper::toDomain);    }

    @Override
    public Mono<Boolean> existsByNombre(String nombre) {
        return brandRepository.existsByNombre(nombre);
    }

    @Override
    public Mono<Brand> findByName(String name) {
        return brandRepository.findByNombre(name)
                .map(BrandMapper::toDomain);    }

}