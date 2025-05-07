package com.arka.supplier_service.infraestructure.adapters.driven.db.adapter;

import com.arka.supplier_service.application.ports.output.SupplierPersistencePort;
import com.arka.supplier_service.domain.model.Supplier;
import com.arka.supplier_service.infraestructure.adapters.driven.db.entity.SupplierEntity;
import com.arka.supplier_service.infraestructure.adapters.driven.db.mapper.SupplierPersistenceMapper;
import com.arka.supplier_service.infraestructure.adapters.driven.db.repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SupplierPersistenceAdapter implements SupplierPersistencePort {

    private final SupplierRepository supplierRepository;

    @Override
    public Mono<Supplier> save(Supplier supplier) {

        UUID id = UUID.randomUUID();
        supplier.setId(id);
        supplier.setCreatedAt(LocalDateTime.now());
        supplier.setActive(true);

        return supplierRepository.insertSupplier(
                        supplier.getId(),
                        supplier.getName(),
                        supplier.getNit(),
                        supplier.getContactName(),
                        supplier.getContactEmail(),
                        supplier.getContactPhone(),
                        supplier.getActive(),
                        supplier.getCreatedAt()
                )
                .thenReturn(supplier);
  }

    @Override
    public Mono<Supplier> update(UUID id, Supplier supplier) {
        return supplierRepository.findById(id)

                .flatMap(existing -> {
                    SupplierEntity updated = SupplierPersistenceMapper.toEntity(supplier);
                    return supplierRepository.save(updated);
                })
                .map(SupplierPersistenceMapper::toDomain);    }

    @Override
    public Mono<Supplier> findById(UUID id) {
        return supplierRepository.findById(id)

                .map(SupplierPersistenceMapper::toDomain);    }

    @Override
    public Flux<Supplier> findAll() {
        return supplierRepository.findAll()
                .map(SupplierPersistenceMapper::toDomain);    }

    @Override
    public Flux<Supplier> findByName(String name) {
        return supplierRepository.findByNameContainingIgnoreCase(name)

                .map(SupplierPersistenceMapper::toDomain);    }

    @Override
    public Flux<Supplier> findActive() {
        return supplierRepository.findByActiveTrue()
                .map(SupplierPersistenceMapper::toDomain);    }

    @Override
    public Mono<Void> activate(UUID id) {
        return supplierRepository.findById(id)

                .flatMap(existing -> {
                    existing.setActive(true);
                    return supplierRepository.save(existing);
                })
                .then();    }

    @Override
    public Mono<Void> deactivate(UUID id) {
        return supplierRepository.findById(id)

                .flatMap(existing -> {
                    existing.setActive(false);
                    return supplierRepository.save(existing);
                })
                .then();    }

    @Override
    public Mono<Void> delete(UUID id) {
        return supplierRepository.deleteById(id);
    }
}
