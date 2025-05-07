package com.arka.supplier_service.infraestructure.adapters.driven.db.repository;

import com.arka.supplier_service.infraestructure.adapters.driven.db.entity.SupplierReceiptDetailEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface SupplierReceiptDetailRepository extends ReactiveCrudRepository<SupplierReceiptDetailEntity, UUID> {

    @Query("""
    INSERT INTO supplierreceiptdetail (id, supplierreceiptid, sku, quantityreceived)
    VALUES (:id, :supplierReceiptId, :sku, :quantityReceived)
""")
    Mono<Void> insertSupplierReceiptDetail(
            @Param("id") UUID id,
            @Param("supplierReceiptId") UUID supplierReceiptId,
            @Param("sku") String sku,
            @Param("quantityReceived") Integer quantityReceived
    );
    Flux<SupplierReceiptDetailEntity> findBySupplierReceiptId(UUID supplierReceiptId);
}
