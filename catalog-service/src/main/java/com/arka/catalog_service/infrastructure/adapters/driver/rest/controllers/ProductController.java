package com.arka.catalog_service.infrastructure.adapters.driver.rest.controllers;

import com.arka.catalog_service.application.ports.input.ProductServicePort;
import com.arka.catalog_service.domain.model.Product;
import com.arka.catalog_service.infrastructure.adapters.driver.rest.dto.CreateProductRequest;
import com.arka.catalog_service.infrastructure.adapters.driver.rest.mapper.ProductRequestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductServicePort service;
    private final ProductRequestMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Product> create(@RequestBody CreateProductRequest request) {
        return mapper.toDomain(request).flatMap(service::createProduct);
    }

    @PutMapping("/{id}")
    public Mono<Product> update(@PathVariable Long id, @RequestBody CreateProductRequest request) {
        return mapper.toDomain(request).flatMap(product -> service.updateProduct(id, product));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> delete(@PathVariable Long id) {
        return service.deleteProduct(id);
    }

    @PatchMapping("/{id}/toggle")
    public Mono<Product> toggleStatus(@PathVariable Long id) {
        return service.toggleProductStatus(id);
    }

    @GetMapping
    public Flux<Product> getAll() {
        return service.getAllProducts();
    }

    @GetMapping("/{id}")
    public Mono<Product> getById(@PathVariable Long id) {
        return service.getProductById(id);
    }

    // 🔍 Filtros
    @GetMapping("/filter/brand")
    public Flux<Product> filterByBrand(@RequestParam String name) {
        return service.filterByBrand(name);
    }

    @GetMapping("/filter/category")
    public Flux<Product> filterByCategory(@RequestParam String name) {
        return service.filterByCategory(name);
    }

    @GetMapping("/filter/feature")
    public Flux<Product> filterByFeature(@RequestParam String name, @RequestParam String value) {
        return service.filterByFeature(name, value);
    }

    @GetMapping("/filter/sku")
    public Flux<Product> filterBySku(@RequestParam String sku) {
        return service.filterBySku(sku);
    }

    @GetMapping("/filter/price")
    public Flux<Product> filterByPrice(@RequestParam Double min, @RequestParam Double max) {
        return service.filterByPriceRange(min, max);
    }

    @GetMapping("/filter")
    public Flux<Product> filter(
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String feature,
            @RequestParam(required = false) String value,
            @RequestParam(required = false) String sku,
            @RequestParam(required = false) Double min,
            @RequestParam(required = false) Double max
    ) {
        return service.filterProducts(brand, category, feature, value, sku, min, max);
    }




}
