package com.arka.catalog_service.application.ports.input;


import com.arka.catalog_service.domain.model.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductServicePort {
    Mono<Product> createProduct(Product product);
    Mono<Product> updateProduct(Long id, Product product);
    Mono<Void> deleteProduct(Long id);
    Mono<Product> toggleProductStatus(Long id);
    Flux<Product> getAllProducts();
    Mono<Product> getProductById(Long id);

    // Filtros
    Flux<Product> filterByBrand(String brandName);
    Flux<Product> filterByCategory(String categoryName);
    Flux<Product> filterByFeature(String featureName, String value);
    Flux<Product> filterBySku(String sku);
    Flux<Product> filterByPriceRange(Double min, Double max);

    Flux<Product> filterProducts(String brand, String category, String feature, String value, String sku, Double min, Double max);

}

