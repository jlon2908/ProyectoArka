package com.arka.catalog_service.application.exception.product;


public class DuplicateSkuException extends RuntimeException {
    public DuplicateSkuException(String sku) {
        super("SKU already exists: " + sku);
    }
}
