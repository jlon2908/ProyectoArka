package com.arka.supplier_service.infraestructure.adapters.driven.db.repository;

import com.arka.supplier_service.infraestructure.adapters.driven.db.entity.SupplierReceiptEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public interface SupplierReceiptRepository extends ReactiveCrudRepository<SupplierReceiptEntity, UUID> {
    @Query("""
    INSERT INTO supplierreceipt (id, supplierorderid, createdat, receivedby, notes)
    VALUES (:id, :supplierOrderId, :createdAt, :receivedBy, :notes)
""")
    Mono<Void> insertSupplierReceipt(
            @Param("id") UUID id,
            @Param("supplierOrderId") UUID supplierOrderId,
            @Param("createdAt") LocalDateTime createdAt,
            @Param("receivedBy") String receivedBy,
            @Param("notes")String notes
    );

    @Query("""
        SELECT sr.*
        FROM supplierreceipt sr
        JOIN supplierorder so ON sr.supplierorderid = so.id
        JOIN supplier s ON so.supplierid = s.id
        WHERE LOWER(s.name) LIKE LOWER(CONCAT('%', :name, '%'))
        """)
    Flux<SupplierReceiptEntity> findBySupplierName(@Param("name") String name);

    @Query("""
        SELECT sr.*
        FROM supplierreceipt sr
        JOIN supplierorder so ON sr.supplierorderid = so.id
        WHERE LOWER(so.ordercode) LIKE LOWER(CONCAT('%', :code, '%'))
        """)
    Flux<SupplierReceiptEntity> findByOrderCode(@Param("code") String code);

    @Query("SELECT * FROM supplierreceipt WHERE LOWER(receivedby) LIKE LOWER(CONCAT('%', :receivedBy, '%'))")
    Flux<SupplierReceiptEntity> findByReceivedBy(@Param("receivedBy") String receivedBy);

    @Query("SELECT * FROM supplierreceipt WHERE createdat BETWEEN :from AND :to")
    Flux<SupplierReceiptEntity> findByCreatedAtBetween(@Param("from") LocalDate from, @Param("to") LocalDate to);

    // Filtro combinado: todos los campos opcionales
    @Query("""
        SELECT sr.*
        FROM supplierreceipt sr
        JOIN supplierorder so ON sr.supplierorderid = so.id
        JOIN supplier s ON so.supplierid = s.id
        WHERE (:supplierName IS NULL OR LOWER(s.name) LIKE LOWER(CONCAT('%', :supplierName, '%')))
          AND (:orderCode IS NULL OR LOWER(so.ordercode) LIKE LOWER(CONCAT('%', :orderCode, '%')))
          AND (:receivedBy IS NULL OR LOWER(sr.receivedby) LIKE LOWER(CONCAT('%', :receivedBy, '%')))
          AND (:from IS NULL OR :to IS NULL OR sr.createdat BETWEEN :from AND :to)
        """)
    Flux<SupplierReceiptEntity> filterReceipts(
            @Param("supplierName") String supplierName,
            @Param("orderCode") String orderCode,
            @Param("receivedBy") String receivedBy,
            @Param("from") LocalDateTime from,
            @Param("to") LocalDateTime to
    );
}
