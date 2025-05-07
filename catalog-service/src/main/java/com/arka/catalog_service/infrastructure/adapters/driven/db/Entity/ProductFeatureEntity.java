package com.arka.catalog_service.infrastructure.adapters.driven.db.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("product_feature")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductFeatureEntity {



    @Column("product_id")
    private Long productId;

    @Column("feature_id")
    private Long featureId;

    @Column("attribute_value")
    private String attributeValue;


}
