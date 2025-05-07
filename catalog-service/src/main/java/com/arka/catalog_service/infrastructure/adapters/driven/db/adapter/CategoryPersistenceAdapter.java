package com.arka.catalog_service.infrastructure.adapters.driven.db.adapter;

import com.arka.catalog_service.application.ports.output.CategoryPersistencePort;
import com.arka.catalog_service.domain.model.Category;
import com.arka.catalog_service.infrastructure.adapters.driven.db.Entity.CategoryEntity;
import com.arka.catalog_service.infrastructure.adapters.driven.db.Mapper.CategoryPersistenceMapper;
import com.arka.catalog_service.infrastructure.adapters.driven.db.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CategoryPersistenceAdapter implements CategoryPersistencePort {

    private final CategoryRepository repository;
    private final CategoryPersistenceMapper mapper;

    @Override
    public Mono<Category> save(Category category) {
        return repository.save(mapper.toEntity(category))
                .map(mapper::toModel);    }

    @Override
    public Mono<Category> update(Long id, Category category) {
        return repository.findById(id)
                .flatMap(existing -> {
                    CategoryEntity updated = mapper.toEntity(category);
                    updated.setId(id);
                    return repository.save(updated);
                })
                .map(mapper::toModel);    }

    @Override
    public Mono<Void> delete(Long id) {
        return repository.deleteById(id);
    }

    @Override
    public Mono<Category> findById(Long id) {
        return repository.findById(id)
                .map(mapper::toModel);    }

    @Override
    public Flux<Category> findAll() {
        return repository.findAll()
                .map(mapper::toModel);    }

    @Override
    public Mono<Boolean> existsByName(String name) {
        return repository.existsByName(name);
    }

    @Override
    public Mono<Category> findByName(String name) {
        return repository.findByName(name)
                .map(mapper::toModel);    }
}
