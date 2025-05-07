package com.arka.supplier_service.infraestructure.adapters.driven.db.adapter;

import com.arka.supplier_service.application.ports.output.SupplierReceiptPersistencePort;
import com.arka.supplier_service.domain.model.SupplierReceipt;
import com.arka.supplier_service.infraestructure.adapters.driven.db.mapper.SupplierReceiptPersistenceMapper;
import com.arka.supplier_service.infraestructure.adapters.driven.db.repository.SupplierReceiptDetailRepository;
import com.arka.supplier_service.infraestructure.adapters.driven.db.repository.SupplierReceiptRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SupplierReceiptPersistenceAdapter implements SupplierReceiptPersistencePort {
    private final SupplierReceiptRepository receiptRepository;
    private  final SupplierReceiptDetailRepository detailRepository;

    @Override
    public Mono<SupplierReceipt> save(SupplierReceipt receipt) {
        UUID receiptId = receipt.getId();

        return receiptRepository.insertSupplierReceipt(
                        receiptId,
                        receipt.getSupplierOrderId(),
                        receipt.getReceivedDate(),
                        receipt.getReceivedBy(),
                        receipt.getNotes()
                )
                .thenMany(Flux.fromIterable(receipt.getDetails()))
                .flatMap(detail -> detailRepository.insertSupplierReceiptDetail(
                        detail.getId(),
                        detail.getSupplierReceiptId(),
                        detail.getSku(),
                        detail.getQuantityReceived()
                ))
                .then(Mono.just(receipt));
    }

    @Override
    public Flux<SupplierReceipt> findBySupplierName(String name) {
        return receiptRepository.findBySupplierName(name)
                .flatMap(entity ->
                                detailRepository.findBySupplierReceiptId(entity.getId())
                                        .map(SupplierReceiptPersistenceMapper::toDetailDomain)
                                        .collectList()
                                        .map(details -> SupplierReceiptPersistenceMapper.toDomain(entity, details))
                        );   }

    @Override
    public Flux<SupplierReceipt> findByOrderCode(String code) {
        return receiptRepository.findByOrderCode(code)
                .flatMap(entity ->
                                detailRepository.findBySupplierReceiptId(entity.getId())
                                        .map(SupplierReceiptPersistenceMapper::toDetailDomain)
                                        .collectList()
                                        .map(details -> SupplierReceiptPersistenceMapper.toDomain(entity, details))
                        );    }

    @Override
    public Flux<SupplierReceipt> findByReceivedBy(String receivedBy) {
        return receiptRepository.findByReceivedBy(receivedBy)
                .flatMap(entity ->
                                detailRepository.findBySupplierReceiptId(entity.getId())
                                        .map(SupplierReceiptPersistenceMapper::toDetailDomain)
                                        .collectList()
                                        .map(details -> SupplierReceiptPersistenceMapper.toDomain(entity, details))
                      );    }

    @Override
    public Flux<SupplierReceipt> findByCreatedAtBetween(LocalDate from, LocalDate to) {
        return receiptRepository.findByCreatedAtBetween(from, to)
                .flatMap(entity ->
                                detailRepository.findBySupplierReceiptId(entity.getId())
                                        .map(SupplierReceiptPersistenceMapper::toDetailDomain)
                                        .collectList()
                                        .map(details -> SupplierReceiptPersistenceMapper.toDomain(entity, details))
                        );
    }

    @Override
    public Flux<SupplierReceipt> filterReceipts(String supplierName, String orderCode, String receivedBy, LocalDateTime from, LocalDateTime to) {
        return receiptRepository.filterReceipts(supplierName, orderCode, receivedBy, from, to)
                .flatMap(entity ->
                                detailRepository.findBySupplierReceiptId(entity.getId())
                                        .map(SupplierReceiptPersistenceMapper::toDetailDomain)
                                        .collectList()
                                        .map(details -> SupplierReceiptPersistenceMapper.toDomain(entity, details))
                       );    }
}
