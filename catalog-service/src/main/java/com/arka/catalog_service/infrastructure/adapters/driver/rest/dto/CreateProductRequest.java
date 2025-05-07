package com.arka.catalog_service.infrastructure.adapters.driver.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductRequest {

    private String name;
    private String description;
    private BigDecimal unitPrice;
    private String sku;
    private String brandName;
    private List<String> categoryNames;

    private List<FeatureValueRequest> features;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FeatureValueRequest {
        private String featureName;
        private String value;
    }
}
