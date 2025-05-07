package com.arka.supplier_service.application.ports.input;

import com.arka.supplier_service.domain.model.SupplierReceipt;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface SupplierReceiptServicePort {
    Mono<SupplierReceipt> create(SupplierReceipt receipt);
    Flux<SupplierReceipt> findBySupplierName(String name);
    Flux<SupplierReceipt> findByOrderCode(String code);
    Flux<SupplierReceipt> findByReceivedBy(String receivedBy);
    Flux<SupplierReceipt> findByCreatedAtBetween(LocalDateTime from, LocalDateTime to);
    Flux<SupplierReceipt> filterReceipts(
            String supplierName,
            String orderCode,
            String receivedBy,
            LocalDateTime from,
            LocalDateTime to
    );
}