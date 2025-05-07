package com.arka.catalog_service.infrastructure.adapters.driven.db.repository;

import com.arka.catalog_service.infrastructure.adapters.driven.db.Entity.ProductCategoryEntity;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductCategoryRepository extends ReactiveCrudRepository<ProductCategoryEntity,Long> {

    @Query("SELECT * FROM product_category WHERE product_id = :productId")
    Flux<ProductCategoryEntity> findAllByProductId(Long productId);

    @Modifying
    @Query("DELETE FROM product_category WHERE product_id = :productId")
    Mono<Void> deleteByProductId(Long productId);


}
