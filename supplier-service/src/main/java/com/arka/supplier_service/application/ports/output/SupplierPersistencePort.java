package com.arka.supplier_service.application.ports.output;

import com.arka.supplier_service.domain.model.Supplier;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface SupplierPersistencePort {
    Mono<Supplier> save(Supplier supplier);

    Mono<Supplier> update(UUID id, Supplier supplier);

    Mono<Supplier> findById(UUID id);

    Flux<Supplier> findAll();

    Flux<Supplier> findByName(String name);

    Flux<Supplier> findActive();

    Mono<Void> activate(UUID id);

    Mono<Void> deactivate(UUID id);

    Mono<Void> delete(UUID id);

}
