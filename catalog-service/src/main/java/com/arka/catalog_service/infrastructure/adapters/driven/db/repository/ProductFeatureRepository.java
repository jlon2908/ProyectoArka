package com.arka.catalog_service.infrastructure.adapters.driven.db.repository;

import com.arka.catalog_service.infrastructure.adapters.driven.db.Entity.ProductFeatureEntity;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductFeatureRepository extends ReactiveCrudRepository<ProductFeatureEntity,Long> {

    Flux<ProductFeatureEntity> findByProductId(Long productId);

    Flux<ProductFeatureEntity> findByFeatureId(Long featureId);

    @Modifying
    @Query("DELETE FROM product_feature WHERE product_id = :productId")
    Mono<Void> deleteByProductId(Long productId);

    @Query("SELECT * FROM product_feature WHERE product_id = :productId")
    Flux<ProductFeatureEntity> findAllByProductId(Long productId);

}
