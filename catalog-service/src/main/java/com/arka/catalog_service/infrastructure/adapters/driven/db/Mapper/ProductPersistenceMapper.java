package com.arka.catalog_service.infrastructure.adapters.driven.db.Mapper;

import com.arka.catalog_service.domain.model.Brand;
import com.arka.catalog_service.domain.model.Category;
import com.arka.catalog_service.domain.model.Product;
import com.arka.catalog_service.domain.model.ProductFeature;
import com.arka.catalog_service.infrastructure.adapters.driven.db.Entity.BrandEntity;
import com.arka.catalog_service.infrastructure.adapters.driven.db.Entity.CategoryEntity;
import com.arka.catalog_service.infrastructure.adapters.driven.db.Entity.ProductEntity;
import com.arka.catalog_service.infrastructure.adapters.driven.db.Entity.ProductFeatureEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductPersistenceMapper {
    public ProductEntity toEntity(Product domain) {
        ProductEntity entity = new ProductEntity();
        entity.setId(domain.getId());
        entity.setName(domain.getName());
        entity.setDescription(domain.getDescription());
        entity.setUnitPrice(domain.getUnitPrice());
        entity.setSku(domain.getSku());
        entity.setActive(domain.isActive());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setBrandId(domain.getBrand().getId());
        return entity;
    }
    public Product toDomain(ProductEntity entity, BrandEntity brand, List<CategoryEntity> categories, List<ProductFeatureEntity> featureEntities) {
        Brand brandModel = new Brand(brand.getId(), brand.getNombre());

        List<Category> categoryModels = categories.stream()
                .map(cat -> new Category(cat.getId(), cat.getName(), cat.getDescription()))
                .collect(Collectors.toList());

        List<ProductFeature> featureModels = featureEntities.stream()
                .map(f -> new ProductFeature(
                        f.getProductId(),
                        f.getFeatureId(),
                        f.getAttributeValue()))
                .collect(Collectors.toList());
        return new Product(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getUnitPrice(),
                entity.getSku(),
                entity.isActive(),
                entity.getCreatedAt(),
                brandModel,
                categoryModels,
                featureModels
        );
    }
}
