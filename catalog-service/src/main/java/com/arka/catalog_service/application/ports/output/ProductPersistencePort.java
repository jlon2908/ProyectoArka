package com.arka.catalog_service.application.ports.output;

import com.arka.catalog_service.domain.model.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductPersistencePort {
    Mono<Product> save(Product product);
    Mono<Product> update(Long id, Product product);
    Mono<Void> deleteById(Long id);
    Mono<Product> findById(Long id);
    Flux<Product> findAll();

    // Filtros
    Flux<Product> findByBrand(String brandName);
    Flux<Product> findByCategory(String categoryName);
    Flux<Product> findByFeature(String featureName, String value);
    Flux<Product> findBySku(String sku);
    Flux<Product> findByPriceRange(Double minPrice, Double maxPrice);

}
