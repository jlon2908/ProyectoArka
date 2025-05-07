package com.arka.supplier_service.infraestructure.adapters.driven.db.entity;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table("supplierreceipt")
public class SupplierReceiptEntity {

    @Id
    private UUID id;

    @Column("supplierorderid")
    private UUID supplierOrderId;

    @Column("createdat")
    private LocalDateTime  createdAt;

    @Column("receivedby")
    private String receivedBy;

    private String notes;
}
