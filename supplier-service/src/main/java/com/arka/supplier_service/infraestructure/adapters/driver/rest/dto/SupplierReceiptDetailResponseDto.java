package com.arka.supplier_service.infraestructure.adapters.driver.rest.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class SupplierReceiptDetailResponseDto {
    private UUID id;
    private String sku;
    private Integer quantityReceived;

}
