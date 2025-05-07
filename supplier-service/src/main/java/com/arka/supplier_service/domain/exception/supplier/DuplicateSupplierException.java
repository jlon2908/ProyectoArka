package com.arka.supplier_service.domain.exception.supplier;

public class DuplicateSupplierException extends RuntimeException {
    public DuplicateSupplierException(String field, String value) {
        super("A supplier with " + field + " '" + value + "' already exists");
    }
}
