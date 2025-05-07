package com.arka.supplier_service.domain.model;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SupplierReceipt {
    private UUID id;
    private UUID supplierOrderId;
    private String receivedBy;
    private String notes;
    private LocalDateTime receivedDate;
    private List<SupplierReceiptDetail> details;

}
