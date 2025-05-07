package com.arka.supplier_service.domain.exception.supplier;

public class SupplierNotFoundByNameException extends RuntimeException {
    public SupplierNotFoundByNameException(String name) {
        super("No suppliers found with name '" + name + "'");
    }
}