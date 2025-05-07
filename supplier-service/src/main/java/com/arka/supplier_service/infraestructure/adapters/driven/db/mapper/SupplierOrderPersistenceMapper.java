package com.arka.supplier_service.infraestructure.adapters.driven.db.mapper;

import com.arka.supplier_service.domain.model.SupplierOrder;
import com.arka.supplier_service.domain.model.SupplierOrderDetail;
import com.arka.supplier_service.infraestructure.adapters.driven.db.entity.SupplierOrderDetailEntity;
import com.arka.supplier_service.infraestructure.adapters.driven.db.entity.SupplierOrderEntity;

public class SupplierOrderPersistenceMapper {
    public static SupplierOrderDetail toDomainDetail(SupplierOrderDetailEntity entity) {
        return SupplierOrderDetail.builder()
                .id(entity.getId())
                .supplierOrderId(entity.getSupplierOrderId())
                .sku(entity.getSku())
                .quantityOrdered(entity.getQuantityOrdered())
                .unitPrice(entity.getUnitPrice())
                .build();
    }


    public static SupplierOrder toDomain(SupplierOrderEntity entity, java.util.List<SupplierOrderDetail> details) {
        return SupplierOrder.builder()
                .id(entity.getId())
                .supplierId(entity.getSupplierId())
                .orderCode(entity.getOrderCode())
                .orderDate(entity.getOrderDate())
                .expectedDeliveryDate(entity.getExpectedDeliveryDate())
                .totalAmount(entity.getTotalAmount())
                .orderDetails(details)
                .build();
    }



}
