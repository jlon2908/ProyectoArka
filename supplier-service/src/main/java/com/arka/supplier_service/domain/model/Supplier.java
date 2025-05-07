package com.arka.supplier_service.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Supplier {
    private UUID id;
    private String name;
    private String nit;
    private String contactName;
    private String contactEmail;
    private String contactPhone;
    private Boolean active;
    private LocalDateTime createdAt;
}
