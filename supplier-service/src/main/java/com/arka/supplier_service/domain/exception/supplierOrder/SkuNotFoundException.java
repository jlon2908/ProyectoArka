package com.arka.supplier_service.domain.exception.supplierOrder;

public class SkuNotFoundException extends RuntimeException {
    public SkuNotFoundException(String sku) {
        super("SKU not found in catalog: " + sku);
    }
}
