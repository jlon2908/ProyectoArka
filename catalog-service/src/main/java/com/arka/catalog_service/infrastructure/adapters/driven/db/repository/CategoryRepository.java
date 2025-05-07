package com.arka.catalog_service.infrastructure.adapters.driven.db.repository;

import com.arka.catalog_service.infrastructure.adapters.driven.db.Entity.CategoryEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface CategoryRepository extends ReactiveCrudRepository<CategoryEntity, Long> {
    Mono<Boolean> existsByName(String name);
    Mono<CategoryEntity> findByName(String name);
    @Query("""
    SELECT c.* FROM category c
    JOIN product_category pc ON pc.category_id = c.id
    WHERE pc.product_id = :productId
""")
    Flux<CategoryEntity> findAllByProductId(Long productId);
}