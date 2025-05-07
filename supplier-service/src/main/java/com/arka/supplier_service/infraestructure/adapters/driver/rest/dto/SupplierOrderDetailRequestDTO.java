package com.arka.supplier_service.infraestructure.adapters.driver.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SupplierOrderDetailRequestDTO {
    private String sku;
    private Integer quantityOrdered;
    private BigDecimal unitPrice;
}
