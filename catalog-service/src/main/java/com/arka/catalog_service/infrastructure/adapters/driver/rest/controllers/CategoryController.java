package com.arka.catalog_service.infrastructure.adapters.driver.rest.controllers;

import com.arka.catalog_service.application.ports.input.CategoryServicePort;
import com.arka.catalog_service.domain.model.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryServicePort categoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Category> create(@RequestBody Category category) {
        return categoryService.create(category);
    }

    @PutMapping("/{id}")
    public Mono<Category> update(@PathVariable Long id, @RequestBody Category category) {
        return categoryService.update(id, category);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> delete(@PathVariable Long id) {
        return categoryService.delete(id);
    }

    @GetMapping
    public Flux<Category> getAll() {
        return categoryService.getAll();
    }

    @GetMapping("/{id}")
    public Mono<Category> getById(@PathVariable Long id) {
        return categoryService.getById(id);
    }
}