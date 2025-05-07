package com.arka.supplier_service.infraestructure.adapters.driver.rest.mapper;

import com.arka.supplier_service.domain.model.SupplierOrder;
import com.arka.supplier_service.infraestructure.adapters.driver.rest.dto.SupplierOrderDetailResponseDTO;
import com.arka.supplier_service.infraestructure.adapters.driver.rest.dto.SupplierOrderResponseDTO;

public class SupplierOrderResponseMapper {
    public static SupplierOrderResponseDTO toDto(SupplierOrder order) {
        return SupplierOrderResponseDTO.builder()
                .id(order.getId())
                .supplierId(order.getSupplierId())
                .orderCode(order.getOrderCode())
                .orderDate(order.getOrderDate())
                .expectedDeliveryDate(order.getExpectedDeliveryDate())
                .totalAmount(order.getTotalAmount())
                .orderDetails(
                        order.getOrderDetails().stream()
                                .map(detail -> SupplierOrderDetailResponseDTO.builder()
                                        .id(detail.getId())
                                        .sku(detail.getSku())
                                        .quantityOrdered(detail.getQuantityOrdered())
                                        .unitPrice(detail.getUnitPrice())
                                        .build())
                                .toList()
                )
                .build();
    }
}
