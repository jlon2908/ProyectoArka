package com.arka.supplier_service.infraestructure.adapters.driver.rest.mapper;

import com.arka.supplier_service.domain.model.SupplierOrder;
import com.arka.supplier_service.domain.model.SupplierOrderDetail;
import com.arka.supplier_service.infraestructure.adapters.driver.rest.dto.SupplierOrderDetailRequestDTO;
import com.arka.supplier_service.infraestructure.adapters.driver.rest.dto.SupplierOrderRequestDTO;

import java.util.stream.Collectors;

public class SupplierOrderRequestMapper {
    public static SupplierOrder toDomain(SupplierOrderRequestDTO dto) {
        return SupplierOrder.builder()
                .supplierId(dto.getSupplierId())
                .orderCode(dto.getOrderCode())
                .orderDate(dto.getOrderDate())
                .expectedDeliveryDate(dto.getExpectedDeliveryDate())
                .orderDetails(
                        dto.getOrderDetails().stream()
                                .map(SupplierOrderRequestMapper::toDetailDomain)
                                .collect(Collectors.toList())
                )
                .build();
    }

    private static SupplierOrderDetail toDetailDomain(SupplierOrderDetailRequestDTO dto) {
        return SupplierOrderDetail.builder()
                .sku(dto.getSku())
                .quantityOrdered(dto.getQuantityOrdered())
                .unitPrice(dto.getUnitPrice())
                .build();
    }
}
