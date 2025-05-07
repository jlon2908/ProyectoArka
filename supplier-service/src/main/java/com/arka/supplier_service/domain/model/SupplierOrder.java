package com.arka.supplier_service.domain.model;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class SupplierOrder {

        private UUID id;
        private UUID supplierId;
        private String orderCode;
        private LocalDate orderDate;
        private LocalDate expectedDeliveryDate;
        private BigDecimal totalAmount;


        @Builder.Default
        private List<SupplierOrderDetail> orderDetails = new ArrayList<>();
}
