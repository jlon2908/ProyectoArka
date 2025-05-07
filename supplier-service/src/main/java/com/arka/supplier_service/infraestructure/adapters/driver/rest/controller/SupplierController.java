package com.arka.supplier_service.infraestructure.adapters.driver.rest.controller;

import com.arka.supplier_service.application.ports.input.SupplierServicePort;
import com.arka.supplier_service.domain.model.Supplier;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/api/suppliers")
@RequiredArgsConstructor
public class SupplierController {

    private final SupplierServicePort supplierServicePort;

    @PostMapping
    public Mono<ResponseEntity<Supplier>> createSupplier(@RequestBody Supplier supplier) {
        return supplierServicePort.create(supplier)
                .map(savedSupplier -> ResponseEntity.status(HttpStatus.CREATED).body(savedSupplier));
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Supplier>> updateSupplier(@PathVariable UUID id, @RequestBody Supplier supplier) {
        return supplierServicePort.update(id, supplier)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Supplier>> getSupplierById(@PathVariable UUID id) {
        return supplierServicePort.findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping
    public Flux<Supplier> getAllSuppliers() {
        return supplierServicePort.findAll();
    }

    @GetMapping("/filter")
    public Flux<Supplier> getSuppliersByName(@RequestParam String name) {
        return supplierServicePort.findByName(name);
    }

    @GetMapping("/active")
    public Flux<Supplier> getActiveSuppliers() {
        return supplierServicePort.findActive();
    }

    @PutMapping("/{id}/activate")
    public Mono<ResponseEntity<Void>> activateSupplier(@PathVariable UUID id) {
        return supplierServicePort.activate(id)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }

    @PutMapping("/{id}/deactivate")
    public Mono<ResponseEntity<Void>> deactivateSupplier(@PathVariable UUID id) {
        return supplierServicePort.deactivate(id)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteSupplier(@PathVariable UUID id) {
        return supplierServicePort.delete(id)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }

}
