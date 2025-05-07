package com.arka.catalog_service.application.ports.input;

import com.arka.catalog_service.domain.model.Category;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CategoryServicePort {
    Mono<Category> create(Category category);
    Mono<Category> update(Long id, Category category);
    Mono<Void> delete(Long id);
    Mono<Category> getById(Long id);
    Flux<Category> getAll();
}