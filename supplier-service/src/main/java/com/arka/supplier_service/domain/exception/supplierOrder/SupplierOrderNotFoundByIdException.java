package com.arka.supplier_service.domain.exception.supplierOrder;

public class SupplierOrderNotFoundByIdException extends RuntimeException {
    public SupplierOrderNotFoundByIdException(String id) {
        super("Supplier order with ID '" + id + "' not found");
    }
}
