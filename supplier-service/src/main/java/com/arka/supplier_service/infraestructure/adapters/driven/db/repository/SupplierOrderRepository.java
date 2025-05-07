package com.arka.supplier_service.infraestructure.adapters.driven.db.repository;

import com.arka.supplier_service.infraestructure.adapters.driven.db.entity.SupplierOrderEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public interface SupplierOrderRepository extends ReactiveCrudRepository<SupplierOrderEntity, UUID> {

    @Query("""
        INSERT INTO supplierorder (id, supplierid, ordercode, orderdate, expecteddeliverydate, totalamount)
        VALUES (:id, :supplierId, :orderCode, :orderDate, :expectedDeliveryDate, :totalAmount)
        """)
    Mono<Void> insertSupplierOrder(
            UUID id,
            UUID supplierId,
            String orderCode,
            LocalDate orderDate,
            LocalDate expectedDeliveryDate,
            BigDecimal totalAmount
    );

    Mono<SupplierOrderEntity> findByOrderCode(String orderCode);

    Flux<SupplierOrderEntity> findBySupplierId(UUID supplierId);
}
