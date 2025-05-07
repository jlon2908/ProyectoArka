package com.arka.supplier_service.infraestructure.adapters.driver.rest.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class SupplierOrderDetailResponseDTO {
    private UUID id;
    private String sku;
    private int quantityOrdered;
    private BigDecimal unitPrice;
}