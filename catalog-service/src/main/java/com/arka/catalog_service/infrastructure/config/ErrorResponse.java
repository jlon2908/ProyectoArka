package com.arka.catalog_service.infrastructure.config;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class ErrorResponse {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String path;
    private String message;
    private String errorCode;
    private List<ValidationErrorDetail> validationErrors;
}