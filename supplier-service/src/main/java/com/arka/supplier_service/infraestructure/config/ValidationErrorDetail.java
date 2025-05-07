package com.arka.supplier_service.infraestructure.config;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ValidationErrorDetail {
    private String field;
    private String error;
}
