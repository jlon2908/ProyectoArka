package com.arka.supplier_service.infraestructure.adapters.driver.rest.dto;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SupplierReceiptRequestDto {
    private UUID supplierOrderId;
    private String receivedBy;
    private String notes;
    private List<SupplierReceiptDetailRequestDto> details;

}


