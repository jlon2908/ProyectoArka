package com.arka.supplier_service.domain.exception.supplierOrder;

public class SupplierOrderNotFoundByCodeException extends RuntimeException {
    public SupplierOrderNotFoundByCodeException(String code) {
        super("Supplier order with code '" + code + "' not found");
    }
}
