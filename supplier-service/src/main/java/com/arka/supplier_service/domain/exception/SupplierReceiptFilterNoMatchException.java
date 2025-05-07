package com.arka.supplier_service.domain.exception;


public class SupplierReceiptFilterNoMatchException extends RuntimeException {
    public SupplierReceiptFilterNoMatchException() {
        super("No supplier receipts found matching the provided filters.");
    }
}
