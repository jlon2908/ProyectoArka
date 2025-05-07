package com.arka.supplier_service.infraestructure.adapters.driven.db.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table("supplierorder")
public class SupplierOrderEntity {
    @Id
    private UUID id;

    @Column("supplierid")
    private UUID supplierId;
    @Column("ordercode")
    private String orderCode;
    @Column("orderdate")

    private LocalDate orderDate;

    @Column("expecteddeliverydate")
    private LocalDate expectedDeliveryDate;
    @Column("totalamount")
    private BigDecimal totalAmount;
}
