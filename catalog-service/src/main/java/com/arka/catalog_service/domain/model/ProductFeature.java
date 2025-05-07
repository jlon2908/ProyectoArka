package com.arka.catalog_service.domain.model;

public class ProductFeature {
    private Long productId;
    private Long featureId;
    private String value;

    public ProductFeature(Long productId, Long featureId, String value) {
        this.productId = productId;
        this.featureId = featureId;
        this.value = value;
    }

    public ProductFeature(Long featureId, String value) {
        this.featureId = featureId;
        this.value = value;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getFeatureId() {
        return featureId;
    }

    public void setFeatureId(Long featureId) {
        this.featureId = featureId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}