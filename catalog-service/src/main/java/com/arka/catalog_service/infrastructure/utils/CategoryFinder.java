package com.arka.catalog_service.infrastructure.utils;

import com.arka.catalog_service.application.ports.output.CategoryPersistencePort;
import com.arka.catalog_service.application.exception.Category.CategoryNotFoundException;
import com.arka.catalog_service.domain.model.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CategoryFinder {
    private final CategoryPersistencePort categoryPersistencePort;

    public Mono<Category> findByName(String name) {
        return categoryPersistencePort.findByName(name)
                .switchIfEmpty(Mono.error(new CategoryNotFoundException(name)));
    }
}