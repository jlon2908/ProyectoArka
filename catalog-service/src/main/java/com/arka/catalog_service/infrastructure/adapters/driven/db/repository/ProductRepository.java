package com.arka.catalog_service.infrastructure.adapters.driven.db.repository;

import com.arka.catalog_service.infrastructure.adapters.driven.db.Entity.ProductEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;

public interface ProductRepository extends ReactiveCrudRepository<ProductEntity,Long> {
    Flux<ProductEntity> findByBrandId(Long brandId);

    Flux<ProductEntity> findBySku(String sku);

    Flux<ProductEntity> findByUnitPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);
    @Query("""
        SELECT p.* FROM product p
        JOIN product_category pc ON p.id = pc.product_id
        JOIN category c ON pc.category_id = c.id
        WHERE c.name = :categoryName
    """)
    Flux<ProductEntity> findByCategoryName(String categoryName);

    @Query("""
    SELECT p.* FROM product p
    JOIN product_feature pf ON p.id = pf.product_id
    JOIN feature f ON pf.feature_id = f.id
    WHERE f.name = :name AND pf.attribute_value = :value
""")
    Flux<ProductEntity> findByFeatureNameAndValue(String name, String value);
}
