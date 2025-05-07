package com.arka.catalog_service.infrastructure.adapters.driven.db.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("product_category")@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductCategoryEntity {


    @Column("product_id")
    private Long productId;

    @Column("category_id")
    private Long categoryId;
}
