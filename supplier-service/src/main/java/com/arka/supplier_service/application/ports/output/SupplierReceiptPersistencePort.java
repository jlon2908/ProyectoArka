package com.arka.supplier_service.application.ports.output;

import com.arka.supplier_service.domain.model.SupplierReceipt;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface SupplierReceiptPersistencePort {
    Mono<SupplierReceipt> save(SupplierReceipt receipt);
    Flux<SupplierReceipt> findBySupplierName(String name);
    Flux<SupplierReceipt> findByOrderCode(String code);
    Flux<SupplierReceipt> findByReceivedBy(String receivedBy);
    Flux<SupplierReceipt> findByCreatedAtBetween(LocalDate from, LocalDate to);
    Flux<SupplierReceipt> filterReceipts(String supplierName, String orderCode, String receivedBy, LocalDateTime from, LocalDateTime to);

}
