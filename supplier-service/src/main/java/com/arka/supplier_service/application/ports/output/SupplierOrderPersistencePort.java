package com.arka.supplier_service.application.ports.output;

import com.arka.supplier_service.domain.model.SupplierOrder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface SupplierOrderPersistencePort {
    Mono<SupplierOrder> saveSupplierOrder(SupplierOrder supplierOrder);

    Mono<SupplierOrder> findSupplierOrderById(UUID id);

    Flux<SupplierOrder> findSupplierOrdersBySupplierId(UUID supplierId);

    Mono<SupplierOrder> findSupplierOrderByOrderCode(String orderCode);

    Flux<SupplierOrder> findAllSupplierOrders();
}
