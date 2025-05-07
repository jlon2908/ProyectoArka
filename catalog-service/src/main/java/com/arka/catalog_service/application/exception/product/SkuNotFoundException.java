package com.arka.catalog_service.application.exception.product;

public class SkuNotFoundException extends RuntimeException {
    public SkuNotFoundException(String sku) {
        super("SKU not found: " + sku);
    }
}
