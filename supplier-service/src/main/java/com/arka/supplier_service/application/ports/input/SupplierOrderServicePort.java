package com.arka.supplier_service.application.ports.input;

import com.arka.supplier_service.domain.model.SupplierOrder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface SupplierOrderServicePort {
    Mono<SupplierOrder> createSupplierOrder(SupplierOrder supplierOrder);

    Mono<SupplierOrder> getSupplierOrderById(UUID id);

    Flux<SupplierOrder> getSupplierOrdersBySupplierId(UUID supplierId);

    Mono<SupplierOrder> getSupplierOrderByOrderCode(String orderCode);

    Flux<SupplierOrder> getAllSupplierOrders();
}
