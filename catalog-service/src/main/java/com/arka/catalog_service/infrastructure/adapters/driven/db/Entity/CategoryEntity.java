package com.arka.catalog_service.infrastructure.adapters.driven.db.Entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("category")
public class CategoryEntity {

    @Id
    private Long id;
    private String name;
    private String description;

    public CategoryEntity() {}

    public CategoryEntity(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }

    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
}
