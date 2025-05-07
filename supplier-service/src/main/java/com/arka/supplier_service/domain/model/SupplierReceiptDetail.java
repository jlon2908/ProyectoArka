package com.arka.supplier_service.domain.model;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SupplierReceiptDetail {
    private UUID id;
    private UUID supplierReceiptId;
    private String sku;
    private Integer quantityReceived;
}