package com.arka.catalog_service.domain.model;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Product {
    private Long id;
    private String name;
    private String description;
    private BigDecimal unitPrice;
    private String sku;
    private boolean active;
    private LocalDateTime createdAt;
    private Brand brand;
    private List<Category> categories;
    private List<ProductFeature> features;
}
