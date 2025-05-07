package com.arka.supplier_service.domain.model;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SupplierOrderDetail {
    private UUID id;
    private UUID supplierOrderId;
    private String sku;
    private Integer quantityOrdered;
    private BigDecimal unitPrice;

}
