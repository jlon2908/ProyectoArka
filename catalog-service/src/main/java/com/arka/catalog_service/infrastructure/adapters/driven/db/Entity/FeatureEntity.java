package com.arka.catalog_service.infrastructure.adapters.driven.db.Entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "feature")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeatureEntity {
    @Id
    private Long id;

    private String name;
}
