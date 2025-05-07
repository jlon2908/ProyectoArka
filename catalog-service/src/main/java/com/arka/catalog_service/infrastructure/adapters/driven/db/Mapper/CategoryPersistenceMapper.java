package com.arka.catalog_service.infrastructure.adapters.driven.db.Mapper;

import com.arka.catalog_service.domain.model.Category;
import com.arka.catalog_service.infrastructure.adapters.driven.db.Entity.CategoryEntity;
import org.springframework.stereotype.Component;

@Component
public class CategoryPersistenceMapper {

    public CategoryEntity toEntity(Category category) {
        if (category == null) return null;
        return new CategoryEntity(category.getId(), category.getName(), category.getDescription());
    }

    public Category toModel(CategoryEntity entity) {
        if (entity == null) return null;
        return new Category(entity.getId(), entity.getName(), entity.getDescription());
    }
}