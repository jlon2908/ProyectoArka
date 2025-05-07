package com.arka.catalog_service.infrastructure.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ValidationErrorDetail {
    private String field;
    private String error;
}
