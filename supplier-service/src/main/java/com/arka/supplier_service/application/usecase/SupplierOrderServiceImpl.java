package com.arka.supplier_service.application.usecase;

import com.arka.supplier_service.application.ports.input.SupplierOrderServicePort;
import com.arka.supplier_service.application.ports.output.SupplierOrderPersistencePort;
import com.arka.supplier_service.domain.exception.supplierOrder.DuplicateSupplierOrderCodeException;
import com.arka.supplier_service.domain.exception.supplierOrder.SupplierOrderNotFoundByCodeException;
import com.arka.supplier_service.domain.exception.supplierOrder.SupplierOrderNotFoundByIdException;
import com.arka.supplier_service.domain.exception.supplierOrder.SupplierOrderNotFoundBySupplierException;
import com.arka.supplier_service.domain.model.SupplierOrder;
import com.arka.supplier_service.domain.model.SupplierOrderDetail;
import com.arka.supplier_service.domain.service.SkuExistenceValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SupplierOrderServiceImpl implements SupplierOrderServicePort {

    private final SupplierOrderPersistencePort supplierOrderPersistencePort;
    private final SkuExistenceValidationService skuExistenceValidationService;


    @Override
    public Mono<SupplierOrder> createSupplierOrder(SupplierOrder supplierOrder) {
        if (supplierOrder.getId() == null) {
            supplierOrder.setId(UUID.randomUUID());
        }

        if (supplierOrder.getOrderDate() == null) {
            supplierOrder.setOrderDate(LocalDate.now());
        }

        BigDecimal totalAmount = supplierOrder.getOrderDetails().stream()
                .map(detail -> detail.getUnitPrice().multiply(BigDecimal.valueOf(detail.getQuantityOrdered())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        supplierOrder.setTotalAmount(totalAmount);

        supplierOrder.getOrderDetails().forEach(detail -> {
            if (detail.getId() == null) {
                detail.setId(UUID.randomUUID());
            }
            detail.setSupplierOrderId(supplierOrder.getId());
        });


        List<String> skus = supplierOrder.getOrderDetails().stream()
                .map(SupplierOrderDetail::getSku)
                .toList();
        return skuExistenceValidationService.validateSkusExist(skus)
                .then(supplierOrderPersistencePort.findSupplierOrderByOrderCode(supplierOrder.getOrderCode()))
                .flatMap(existing -> Mono.<SupplierOrder>error(
                        new DuplicateSupplierOrderCodeException(supplierOrder.getOrderCode())))
                .switchIfEmpty(Mono.defer(() -> supplierOrderPersistencePort.saveSupplierOrder(supplierOrder)));
    }
    @Override
    public Mono<SupplierOrder> getSupplierOrderById(UUID id) {
        return supplierOrderPersistencePort.findSupplierOrderById(id)
                .switchIfEmpty(Mono.error(new SupplierOrderNotFoundByIdException(id.toString())));
    }

    @Override
    public Flux<SupplierOrder> getSupplierOrdersBySupplierId(UUID supplierId) {

        return supplierOrderPersistencePort.findSupplierOrdersBySupplierId(supplierId)
                .switchIfEmpty(Flux.error(new SupplierOrderNotFoundBySupplierException(supplierId.toString())));
    }

    @Override
    public Mono<SupplierOrder> getSupplierOrderByOrderCode(String orderCode) {
        return supplierOrderPersistencePort.findSupplierOrderByOrderCode(orderCode)
                .switchIfEmpty(Mono.error(new SupplierOrderNotFoundByCodeException(orderCode)));
    }

    @Override
    public Flux<SupplierOrder> getAllSupplierOrders() {
        return supplierOrderPersistencePort.findAllSupplierOrders();
    }
}
