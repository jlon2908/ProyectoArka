package com.arka.supplier_service.infraestructure.adapters.driven.db.repository;

import com.arka.supplier_service.infraestructure.adapters.driven.db.entity.SupplierOrderDetailEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.UUID;

public interface SupplierOrderDetailRepository extends ReactiveCrudRepository<SupplierOrderDetailEntity, UUID> {
    @Query("""
        INSERT INTO supplierorderdetail (id, supplierorderid, sku, quantityordered, unitprice)
        VALUES (:id, :supplierOrderId, :sku, :quantityOrdered, :unitPrice)
        """)
    Mono<Void> insertSupplierOrderDetail(
            UUID id,
            UUID supplierOrderId,
            String sku,
            Integer quantityOrdered,
            BigDecimal unitPrice
    );

    @Query("SELECT * FROM supplierorderdetail WHERE supplierorderid = :supplierOrderId")
    Flux<SupplierOrderDetailEntity> findBySupplierOrderId(@Param("supplierOrderId") UUID supplierOrderId);
}
