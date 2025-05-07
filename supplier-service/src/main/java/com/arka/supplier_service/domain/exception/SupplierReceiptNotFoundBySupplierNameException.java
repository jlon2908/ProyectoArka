package com.arka.supplier_service.domain.exception;

public class SupplierReceiptNotFoundBySupplierNameException extends RuntimeException {
    public SupplierReceiptNotFoundBySupplierNameException(String name) {
        super("No supplier receipts found for supplier with name '" + name + "'");
    }
}