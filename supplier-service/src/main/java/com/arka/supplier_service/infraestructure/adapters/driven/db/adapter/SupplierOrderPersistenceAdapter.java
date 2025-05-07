package com.arka.supplier_service.infraestructure.adapters.driven.db.adapter;

import com.arka.supplier_service.application.ports.output.SupplierOrderPersistencePort;
import com.arka.supplier_service.application.ports.output.SupplierPersistencePort;
import com.arka.supplier_service.domain.model.Supplier;
import com.arka.supplier_service.domain.model.SupplierOrder;
import com.arka.supplier_service.domain.model.SupplierOrderDetail;
import com.arka.supplier_service.infraestructure.adapters.driven.db.entity.SupplierOrderDetailEntity;
import com.arka.supplier_service.infraestructure.adapters.driven.db.entity.SupplierOrderEntity;
import com.arka.supplier_service.infraestructure.adapters.driven.db.mapper.SupplierOrderPersistenceMapper;
import com.arka.supplier_service.infraestructure.adapters.driven.db.repository.SupplierOrderDetailRepository;
import com.arka.supplier_service.infraestructure.adapters.driven.db.repository.SupplierOrderRepository;
import com.arka.supplier_service.infraestructure.adapters.driven.db.repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SupplierOrderPersistenceAdapter implements SupplierOrderPersistencePort {

    private final SupplierOrderRepository supplierOrderRepository;
    private  final SupplierOrderDetailRepository supplierOrderDetailRepository;


    @Override
    public Mono<SupplierOrder> saveSupplierOrder(SupplierOrder supplierOrder) {
        UUID orderId = supplierOrder.getId();

        return supplierOrderRepository.insertSupplierOrder(
                        orderId,
                        supplierOrder.getSupplierId(),
                        supplierOrder.getOrderCode(),
                        supplierOrder.getOrderDate(),
                        supplierOrder.getExpectedDeliveryDate(),
                        supplierOrder.getTotalAmount()
                )
                .thenMany(Flux.fromIterable(supplierOrder.getOrderDetails()))
                .flatMap(detail -> supplierOrderDetailRepository.insertSupplierOrderDetail(
                        detail.getId(),
                        detail.getSupplierOrderId(),
                        detail.getSku(),
                        detail.getQuantityOrdered(),
                        detail.getUnitPrice()
                ))
                .then(Mono.just(supplierOrder));
    }

    @Override
    public Mono<SupplierOrder> findSupplierOrderById(UUID id) {

        return supplierOrderRepository.findById(id)
                .flatMap(orderEntity ->
                        supplierOrderDetailRepository.findBySupplierOrderId(orderEntity.getId())
                                .map(SupplierOrderPersistenceMapper::toDomainDetail)
                                .collectList()
                                .map(details -> SupplierOrderPersistenceMapper.toDomain(orderEntity, details))
                );
    }


    @Override
    public Flux<SupplierOrder> findSupplierOrdersBySupplierId(UUID supplierId) {
        return supplierOrderRepository.findBySupplierId(supplierId)

                .flatMap(orderEntity ->
                        supplierOrderDetailRepository.findBySupplierOrderId(orderEntity.getId())
                                .map(SupplierOrderPersistenceMapper::toDomainDetail)
                                .collectList()
                                .map(details -> {
                                    SupplierOrder domain = SupplierOrderPersistenceMapper.toDomain(orderEntity,details);
                                    domain.setOrderDetails(details);
                                    return domain;
                                })
                );
    }


    @Override
    public Mono<SupplierOrder> findSupplierOrderByOrderCode(String orderCode) {

        return supplierOrderRepository.findByOrderCode(orderCode)

                .flatMap(orderEntity ->
                        supplierOrderDetailRepository.findBySupplierOrderId(orderEntity.getId())
                                .map(SupplierOrderPersistenceMapper::toDomainDetail)
                                .collectList()
                                .map(details -> {
                                    SupplierOrder domain = SupplierOrderPersistenceMapper.toDomain(orderEntity,details);
                                    domain.setOrderDetails(details);
                                    return domain;
                                })
                );
    }


    @Override
    public Flux<SupplierOrder> findAllSupplierOrders() {
        return supplierOrderRepository.findAll()
                .flatMap(orderEntity ->
                        supplierOrderDetailRepository.findBySupplierOrderId(orderEntity.getId())
                                .map(SupplierOrderPersistenceMapper::toDomainDetail)
                                .collectList()
                                .map(details -> {
                                    SupplierOrder domain = SupplierOrderPersistenceMapper.toDomain(orderEntity,details);
                                    domain.setOrderDetails(details);
                                    return domain;
                                })
                );
    }
}
