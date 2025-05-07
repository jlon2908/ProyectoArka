package com.arka.supplier_service.domain.exception.supplierOrder;

public class SupplierOrderNotFoundBySupplierException extends RuntimeException {
    public SupplierOrderNotFoundBySupplierException(String supplierId) {
        super("No supplier orders found for supplier ID '" + supplierId + "'");
    }
}