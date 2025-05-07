package com.arka.supplier_service.infraestructure.adapters.driver.rest.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class SupplierOrderResponseDTO {
    private UUID id;
    private UUID supplierId;
    private String orderCode;
    private LocalDate orderDate;
    private LocalDate expectedDeliveryDate;
    private BigDecimal totalAmount;
    private List<SupplierOrderDetailResponseDTO> orderDetails;
}