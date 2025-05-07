package com.arka.catalog_service.infrastructure.adapters.driven.db.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table("brand")
public class BrandEntity {
    @Id

    private Long id;

    @Column("name")
    private String nombre;
}
