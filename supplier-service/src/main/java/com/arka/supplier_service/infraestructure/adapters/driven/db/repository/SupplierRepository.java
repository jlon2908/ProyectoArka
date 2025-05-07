package com.arka.supplier_service.infraestructure.adapters.driven.db.repository;

import com.arka.supplier_service.infraestructure.adapters.driven.db.entity.SupplierEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.time.LocalDateTime;

import java.util.UUID;

public interface SupplierRepository extends ReactiveCrudRepository<SupplierEntity, UUID> {
    Flux<SupplierEntity> findByNameContainingIgnoreCase(String name);

    Flux<SupplierEntity> findByActiveTrue();


    @Query("INSERT INTO supplier (id, name, nit, contact_name, contact_email, contact_phone, active, created_at) VALUES (:id, :name, :nit, :contactName, :contactEmail, :contactPhone, :active, :createdAt)")
    Mono<Void> insertSupplier(UUID id, String name, String nit, String contactName, String contactEmail, String contactPhone, Boolean active, LocalDateTime createdAt);


}
