package com.arka.supplier_service.domain.exception.supplierOrderRecipt;

public class SupplierReceiptNotFoundByReceivedByException extends RuntimeException {
    public SupplierReceiptNotFoundByReceivedByException(String receivedBy) {
        super("No supplier receipts found received by '" + receivedBy + "'");
    }
}
