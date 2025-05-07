package com.arka.catalog_service.infrastructure.adapters.driver.rest.controllers;

import com.arka.catalog_service.application.ports.input.BrandServicePort;
import com.arka.catalog_service.domain.model.Brand;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/brands")
@RequiredArgsConstructor
public class BrandController {

    private final BrandServicePort brandService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Brand> create(@RequestBody Brand brand) {
        return brandService.create(brand);
    }

    @PutMapping("/{id}")
    public Mono<Brand> update(@PathVariable Long id, @RequestBody Brand brand) {
        return brandService.update(id, brand);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> delete(@PathVariable Long id) {
        return brandService.delete(id);
    }

    @GetMapping
    public Flux<Brand> getAll() {
        return brandService.getAll();
    }

    @GetMapping("/{id}")
    public Mono<Brand> getById(@PathVariable Long id) {
        return brandService.getById(id);
    }
}

