package com.arka.catalog_service.domain.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Brand {
    private Long id;
    private String nombre;

}