package com.arka.supplier_service.infraestructure.adapters.driven.db.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table("supplierreceiptdetail")
public class SupplierReceiptDetailEntity {

    @Id
    private UUID id;

    @Column("supplierreceiptid")
    private UUID supplierReceiptId;

    private String sku;

    @Column("quantityreceived")
    private Integer quantityReceived;
}