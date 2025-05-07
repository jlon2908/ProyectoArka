package com.arka.supplier_service.infraestructure.adapters.driver.rest.controller;

import com.arka.supplier_service.application.ports.input.SupplierReceiptServicePort;
import com.arka.supplier_service.domain.model.SupplierReceipt;
import com.arka.supplier_service.infraestructure.adapters.driver.rest.dto.SupplierReceiptRequestDto;
import com.arka.supplier_service.infraestructure.adapters.driver.rest.dto.SupplierReceiptResponseDto;
import com.arka.supplier_service.infraestructure.adapters.driver.rest.mapper.SupplierReceiptRestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/receipts")
@RequiredArgsConstructor
public class SupplierReceiptController {

    private final SupplierReceiptServicePort service;

    @PostMapping
    public Mono<SupplierReceiptResponseDto> create(@RequestBody SupplierReceiptRequestDto requestDto) {
        SupplierReceipt receipt = SupplierReceiptRestMapper.toDomain(requestDto);
        return service.create(receipt)
                .map(SupplierReceiptRestMapper::toResponseDto);
    }

    @GetMapping("/by-supplier-name")
    public Flux<SupplierReceiptResponseDto> getBySupplierName(@RequestParam String name) {
        return service.findBySupplierName(name)
                .map(SupplierReceiptRestMapper::toResponseDto);
    }

    @GetMapping("/by-order-code")
    public Flux<SupplierReceiptResponseDto> getByOrderCode(@RequestParam String code) {
        return service.findByOrderCode(code)
                .map(SupplierReceiptRestMapper::toResponseDto);
    }

    @GetMapping("/by-received-by")
    public Flux<SupplierReceiptResponseDto> getByReceivedBy(@RequestParam String receivedBy) {
        return service.findByReceivedBy(receivedBy)
                .map(SupplierReceiptRestMapper::toResponseDto);
    }

    @GetMapping("/by-date-range")
    public Flux<SupplierReceiptResponseDto> getByDateRange(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {
        return service.findByCreatedAtBetween(from, to)
                .map(SupplierReceiptRestMapper::toResponseDto);
    }

    @GetMapping("/filter")
    public Flux<SupplierReceiptResponseDto> filterReceipts(
            @RequestParam(required = false) String supplierName,
            @RequestParam(required = false) String orderCode,
            @RequestParam(required = false) String receivedBy,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to)

    {
        return service.filterReceipts(supplierName, orderCode, receivedBy, from, to)
                .map(SupplierReceiptRestMapper::toResponseDto);
}
}
