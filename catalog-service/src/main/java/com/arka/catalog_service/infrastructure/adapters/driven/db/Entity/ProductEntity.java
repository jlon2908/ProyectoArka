package com.arka.catalog_service.infrastructure.adapters.driven.db.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("product")
public class ProductEntity {
    @Id
    private Long id;
    private String name;
    private String description;
    private BigDecimal unitPrice;
    private String sku;
    private boolean active;
    private LocalDateTime createdAt;
    private Long brandId;
}
