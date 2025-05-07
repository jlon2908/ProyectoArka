package com.arka.supplier_service.domain.exception.supplierOrder;

public class DuplicateSupplierOrderCodeException extends RuntimeException {
    public DuplicateSupplierOrderCodeException(String orderCode) {
        super("Supplier order with code '" + orderCode + "' already exists.");
}
}
