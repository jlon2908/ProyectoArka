package com.arka.catalog_service.application.ports.output;

import com.arka.catalog_service.domain.model.Category;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CategoryPersistencePort {
    Mono<Category> save(Category category);
    Mono<Category> update(Long id, Category category);
    Mono<Void> delete(Long id);
    Mono<Category> findById(Long id);
    Flux<Category> findAll();
    Mono<Boolean> existsByName(String name);
    Mono<Category> findByName(String name);

}