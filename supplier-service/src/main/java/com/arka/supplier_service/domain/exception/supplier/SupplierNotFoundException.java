package com.arka.supplier_service.domain.exception.supplier;

public class SupplierNotFoundException extends RuntimeException {
    public SupplierNotFoundException(String id) {
        super("Supplier with ID " + id + " not found");
    }
}
