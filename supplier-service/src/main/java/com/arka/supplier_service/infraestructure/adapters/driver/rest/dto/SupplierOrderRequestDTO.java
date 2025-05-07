package com.arka.supplier_service.infraestructure.adapters.driver.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SupplierOrderRequestDTO {
    private UUID supplierId;
    private String orderCode;
    private LocalDate orderDate;
    private LocalDate expectedDeliveryDate;
    private List<SupplierOrderDetailRequestDTO> orderDetails;
}
