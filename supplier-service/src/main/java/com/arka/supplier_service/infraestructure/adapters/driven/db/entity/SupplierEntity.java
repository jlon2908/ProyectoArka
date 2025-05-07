package com.arka.supplier_service.infraestructure.adapters.driven.db.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("supplier")
public class SupplierEntity {
    @Id
    private UUID id;

    private String name;
    private String nit;
    @Column("contact_name")
    private String contactName;

    @Column("contact_email")
    private String contactEmail;

    @Column("contact_phone")
    private String contactPhone;
    private Boolean active;

    @Column("created_at")
    private LocalDateTime createdAt;
}
