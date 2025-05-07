package com.arka.catalog_service.application.usecase;

import com.arka.catalog_service.application.ports.input.CategoryServicePort;
import com.arka.catalog_service.application.ports.output.CategoryPersistencePort;
import com.arka.catalog_service.application.exception.Category.CategoryNotFoundException;
import com.arka.catalog_service.application.exception.Category.DuplicateCategoryException;
import com.arka.catalog_service.application.exception.NoDataFoundException;
import com.arka.catalog_service.domain.model.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryServicePort {
    private final CategoryPersistencePort persistencePort;

    @Override
    public Mono<Category> create(Category category) {
        return persistencePort.existsByName(category.getName())
                .flatMap(exists -> {
                    if (Boolean.TRUE.equals(exists)) {
                        return Mono.error(new DuplicateCategoryException(category.getName()));
                    }
                    return persistencePort.save(category);
                });    }

    @Override
    public Mono<Category> update(Long id, Category category) {
        return persistencePort.findById(id)
                .switchIfEmpty(Mono.error(new CategoryNotFoundException(id)))
                .flatMap(existing -> persistencePort.update(id, category));    }

    @Override
    public Mono<Void> delete(Long id) {
        return persistencePort.findById(id)
                .switchIfEmpty(Mono.error(new CategoryNotFoundException(id)))
                .then(persistencePort.delete(id));    }

    @Override
    public Mono<Category> getById(Long id) {
        return persistencePort.findById(id)
                .switchIfEmpty(Mono.error(new CategoryNotFoundException(id)));    }

    @Override
    public Flux<Category> getAll() {

        return persistencePort.findAll()
                .switchIfEmpty(Mono.error(new NoDataFoundException("No categories found.")));    }
}
