package com.arka.catalog_service.infrastructure.adapters.driven.db.adapter;

import com.arka.catalog_service.application.ports.output.ProductPersistencePort;
import com.arka.catalog_service.domain.model.Product;
import com.arka.catalog_service.infrastructure.adapters.driven.db.Entity.ProductCategoryEntity;
import com.arka.catalog_service.infrastructure.adapters.driven.db.Entity.ProductEntity;
import com.arka.catalog_service.infrastructure.adapters.driven.db.Entity.ProductFeatureEntity;
import com.arka.catalog_service.infrastructure.adapters.driven.db.Mapper.ProductPersistenceMapper;
import com.arka.catalog_service.infrastructure.adapters.driven.db.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductPersistenceAdapter implements ProductPersistencePort {

    private final ProductRepository productRepository;
    private final ProductFeatureRepository featureRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;

    private final ProductPersistenceMapper mapper;
    private final ProductCategoryRepository productCategoryRepository;

    @Override
    public Mono<Product> save(Product product) {
        ProductEntity entity = mapper.toEntity(product);

        return productRepository.save(entity)
                .flatMap(saved -> {
                    List<ProductFeatureEntity> features = product.getFeatures().stream()
                            .map(f -> ProductFeatureEntity.builder()
                                    .productId(saved.getId())
                                    .featureId(f.getFeatureId())
                                    .attributeValue(f.getValue())
                                    .build())
                            .toList();

                    List<ProductCategoryEntity> categories = product.getCategories().stream()
                            .map(c -> ProductCategoryEntity.builder()
                                    .productId(saved.getId())
                                    .categoryId(c.getId())
                                    .build())
                            .toList();

                    return featureRepository.saveAll(features)
                            .thenMany(productCategoryRepository.saveAll(categories))
                            .then(Mono.defer(() -> findById(saved.getId())));
                });
    }

    @Override
    public Mono<Product> update(Long id, Product product) {
        return productRepository.findById(id)
                .flatMap(existing -> {
                    ProductEntity updatedEntity = mapper.toEntity(product);
                    updatedEntity.setId(id);

                    return productRepository.save(updatedEntity)
                            .flatMap(saved -> {
                                Mono<Void> deleteFeatures = featureRepository.deleteByProductId(id);
                                Mono<Void> deleteCategories = productCategoryRepository.deleteByProductId(id);

                                List<ProductFeatureEntity> features = product.getFeatures().stream()
                                        .map(f -> ProductFeatureEntity.builder()
                                                .productId(id)
                                                .featureId(f.getFeatureId())
                                                .attributeValue(f.getValue())
                                                .build())
                                        .toList();

                                List<ProductCategoryEntity> categories = product.getCategories().stream()
                                        .map(c -> ProductCategoryEntity.builder()
                                                .productId(id)
                                                .categoryId(c.getId())
                                                .build())
                                        .toList();

                                return Mono.when(deleteFeatures, deleteCategories)
                                        .thenMany(featureRepository.saveAll(features))
                                        .thenMany(productCategoryRepository.saveAll(categories))
                                        .then(Mono.defer(() -> findById(id)));
                            });
                });
    }

    @Override
    public Mono<Void> deleteById(Long id) {
        return featureRepository.deleteByProductId(id)
                .then(productCategoryRepository.deleteByProductId(id))
                .then(productRepository.deleteById(id));
    }

    @Override
    public Mono<Product> findById(Long id) {
        return productRepository.findById(id)
                .flatMap(entity -> Mono.zip(
                        brandRepository.findById(entity.getBrandId()),
                        categoryRepository.findAllByProductId(entity.getId()).collectList(),
                        featureRepository.findAllByProductId(entity.getId()).collectList()
                ).map(tuple -> mapper.toDomain(entity, tuple.getT1(), tuple.getT2(), tuple.getT3())));
    }

    private Mono<Product> buildFullProduct(ProductEntity entity) {
        return Mono.zip(
                brandRepository.findById(entity.getBrandId()),
                categoryRepository.findAllByProductId(entity.getId()).collectList(),
                featureRepository.findAllByProductId(entity.getId()).collectList()
        ).map(tuple -> mapper.toDomain(entity, tuple.getT1(), tuple.getT2(), tuple.getT3()));
    }

    @Override
    public Flux<Product> findAll() {
        return productRepository.findAll()
                .flatMap(this::buildFullProduct);
    }

    @Override
    public Flux<Product> findByBrand(String brandName) {
        return brandRepository.findByNombre(brandName)
                .flatMapMany(brand -> productRepository.findByBrandId(brand.getId())
                        .flatMap(this::buildFullProduct));
    }

    @Override
    public Flux<Product> findByCategory(String categoryName) {
        return productRepository.findByCategoryName(categoryName)
                .flatMap(this::buildFullProduct);
    }

    @Override
    public Flux<Product> findByFeature(String featureName, String value) {
        return productRepository.findByFeatureNameAndValue(featureName, value)
                .flatMap(this::buildFullProduct);
    }

    @Override
    public Flux<Product> findBySku(String sku) {
        return productRepository.findBySku(sku)
                .flatMap(this::buildFullProduct);
    }

    @Override
    public Flux<Product> findByPriceRange(Double minPrice, Double maxPrice) {
        return productRepository.findByUnitPriceBetween(
                        BigDecimal.valueOf(minPrice),
                        BigDecimal.valueOf(maxPrice))
                .flatMap(this::buildFullProduct);
    }


}
