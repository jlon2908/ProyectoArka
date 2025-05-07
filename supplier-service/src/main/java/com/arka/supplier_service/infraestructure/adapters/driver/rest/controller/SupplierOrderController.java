package com.arka.supplier_service.infraestructure.adapters.driver.rest.controller;

import com.arka.supplier_service.application.ports.input.SupplierOrderServicePort;
import com.arka.supplier_service.domain.model.SupplierOrder;
import com.arka.supplier_service.infraestructure.adapters.driver.rest.dto.SupplierOrderRequestDTO;
import com.arka.supplier_service.infraestructure.adapters.driver.rest.dto.SupplierOrderResponseDTO;
import com.arka.supplier_service.infraestructure.adapters.driver.rest.mapper.SupplierOrderRequestMapper;
import com.arka.supplier_service.infraestructure.adapters.driver.rest.mapper.SupplierOrderResponseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/api/supplier-orders")
@RequiredArgsConstructor
public class SupplierOrderController {

    private final SupplierOrderServicePort supplierOrderServicePort;


    @PostMapping
    public Mono<SupplierOrder> createSupplierOrder(@RequestBody SupplierOrderRequestDTO request) {
        SupplierOrder supplierOrder = SupplierOrderRequestMapper.toDomain(request);
        return supplierOrderServicePort.createSupplierOrder(supplierOrder);
    }


    @GetMapping("/{id}")
    public Mono<SupplierOrderResponseDTO> getSupplierOrderById(@PathVariable UUID id) {
        return supplierOrderServicePort.getSupplierOrderById(id)
                .map(SupplierOrderResponseMapper::toDto);
    }

    @GetMapping("/supplier/{supplierId}")
    public Flux<SupplierOrderResponseDTO> getSupplierOrdersBySupplierId(@PathVariable UUID supplierId) {
        return supplierOrderServicePort.getSupplierOrdersBySupplierId(supplierId)
                .map(SupplierOrderResponseMapper::toDto);
    }


    @GetMapping("/code/{orderCode}")
    public Mono<SupplierOrderResponseDTO> getSupplierOrderByOrderCode(@PathVariable String orderCode) {
        return supplierOrderServicePort.getSupplierOrderByOrderCode(orderCode)
                .map(SupplierOrderResponseMapper::toDto);
    }


    @GetMapping
    public Flux<SupplierOrderResponseDTO> getAllSupplierOrders() {
        return supplierOrderServicePort.getAllSupplierOrders()
                .map(SupplierOrderResponseMapper::toDto);
    }
}
