package com.arka.supplier_service.domain.exception.supplierOrderRecipt;

public class DuplicateSupplierReceiptForOrderException extends RuntimeException {
    public DuplicateSupplierReceiptForOrderException(String orderId) {
        super("A receipt already exists for supplier order with ID: " + orderId);
}
}
