package com.arka.supplier_service.domain.exception.supplierOrderRecipt;

import java.time.LocalDate;

public class SupplierReceiptNotFoundByDateRangeException extends RuntimeException {
    public SupplierReceiptNotFoundByDateRangeException(LocalDate start, LocalDate end) {
        super("No supplier receipts found between dates " + start + " and " + end);
    }
}
