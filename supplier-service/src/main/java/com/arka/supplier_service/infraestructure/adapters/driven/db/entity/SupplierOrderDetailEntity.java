package com.arka.supplier_service.infraestructure.adapters.driven.db.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table("supplierorderdetail")
public class SupplierOrderDetailEntity {
    @Id
    private UUID id;

    @Column("supplierorderid")
    private UUID supplierOrderId;

    private String sku;

    @Column("quantityordered")
    private Integer quantityOrdered;

    @Column("unitprice")
    private BigDecimal unitPrice;
}
