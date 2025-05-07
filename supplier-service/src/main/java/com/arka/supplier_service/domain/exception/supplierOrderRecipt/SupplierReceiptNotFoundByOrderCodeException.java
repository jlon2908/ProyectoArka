package com.arka.supplier_service.domain.exception.supplierOrderRecipt;

public class SupplierReceiptNotFoundByOrderCodeException extends RuntimeException {
    public SupplierReceiptNotFoundByOrderCodeException(String orderCode) {
        super("No supplier receipts found with order code '" + orderCode + "'");
    }
}
