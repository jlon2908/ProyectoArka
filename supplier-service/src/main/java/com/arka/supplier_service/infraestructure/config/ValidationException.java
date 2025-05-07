package com.arka.supplier_service.infraestructure.config;

import java.util.List;

public class ValidationException extends RuntimeException {
    private final List<ValidationErrorDetail> errors;

    public ValidationException(List<ValidationErrorDetail> errors) {
        super("Validation failed");
        this.errors = errors;
    }

    public List<ValidationErrorDetail> getErrors() {
        return errors;
    }
}
