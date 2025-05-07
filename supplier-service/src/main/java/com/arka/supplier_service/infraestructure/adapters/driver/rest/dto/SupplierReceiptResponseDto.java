package com.arka.supplier_service.infraestructure.adapters.driver.rest.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class SupplierReceiptResponseDto {
    private UUID id;
    private UUID supplierOrderId;
    private String receivedBy;
    private String notes;
    private LocalDateTime receivedDate;
    private List<SupplierReceiptDetailResponseDto> details;

}
