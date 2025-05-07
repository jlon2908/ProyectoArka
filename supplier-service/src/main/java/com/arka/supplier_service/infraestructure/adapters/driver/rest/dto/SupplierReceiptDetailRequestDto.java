package com.arka.supplier_service.infraestructure.adapters.driver.rest.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SupplierReceiptDetailRequestDto {
    private String sku;
    private Integer quantityReceived;
}
