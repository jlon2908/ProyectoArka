package com.arka.catalog_service.infrastructure.config;

import com.arka.catalog_service.application.exception.Brand.BrandNotFoundException;
import com.arka.catalog_service.application.exception.Brand.DuplicateBrandException;
import com.arka.catalog_service.application.exception.Category.CategoryNotFoundException;
import com.arka.catalog_service.application.exception.Category.DuplicateCategoryException;
import com.arka.catalog_service.application.exception.Feature.DuplicateFeatureException;
import com.arka.catalog_service.application.exception.Feature.FeatureNotFoundException;
import com.arka.catalog_service.application.exception.NoDataFoundException;
import com.arka.catalog_service.application.exception.product.DuplicateSkuException;
import com.arka.catalog_service.application.exception.product.SkuNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.ServerWebInputException;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {


    // === VALIDATION ERRORS ===
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(ValidationException ex, ServerWebExchange exchange) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ErrorResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                        .path(exchange.getRequest().getPath().value())
                        .message("Validation failed")
                        .errorCode("CATALOG-VALIDATION-002")
                        .validationErrors(ex.getErrors())
                        .build()
        );
    }

    // === DUPLICATES ===
    @ExceptionHandler({
            DuplicateBrandException.class,
            DuplicateCategoryException.class,
            DuplicateFeatureException.class
    })
    public ResponseEntity<ErrorResponse> handleDuplicateEntity(RuntimeException ex, ServerWebExchange exchange) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                ErrorResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.CONFLICT.value())
                        .error(HttpStatus.CONFLICT.getReasonPhrase())
                        .path(exchange.getRequest().getPath().value())
                        .message(ex.getMessage())
                        .errorCode("CATALOG-DUPLICATE-409")
                        .validationErrors(null)
                        .build()
        );
    }

    @ExceptionHandler(DuplicateSkuException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateSku(DuplicateSkuException ex, ServerWebExchange exchange) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                ErrorResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.CONFLICT.value())
                        .error(HttpStatus.CONFLICT.getReasonPhrase())
                        .path(exchange.getRequest().getPath().value())
                        .message(ex.getMessage())
                        .errorCode("CATALOG-DUPLICATE-SKU-409")
                        .validationErrors(null)
                        .build()
        );
    }

    @ExceptionHandler(SkuNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleSkuNotFound(SkuNotFoundException ex, ServerWebExchange exchange) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ErrorResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.NOT_FOUND.value())
                        .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                        .path(exchange.getRequest().getPath().value())
                        .message(ex.getMessage())
                        .errorCode("CATALOG-SKU-NOTFOUND-404")
                        .validationErrors(null)
                        .build()
        );
    }

    // === NOT FOUND ===
    @ExceptionHandler({
            BrandNotFoundException.class,
            CategoryNotFoundException.class,
            FeatureNotFoundException.class,
            NoDataFoundException.class
    })
    public ResponseEntity<ErrorResponse> handleNotFound(RuntimeException ex, ServerWebExchange exchange) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ErrorResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.NOT_FOUND.value())
                        .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                        .path(exchange.getRequest().getPath().value())
                        .message(ex.getMessage())
                        .errorCode("CATALOG-NOTFOUND-404")
                        .validationErrors(null)
                        .build()
        );
    }

    // === BAD INPUT FORMAT ===
    @ExceptionHandler(ServerWebInputException.class)
    public ResponseEntity<ErrorResponse> handleWebInput(ServerWebInputException ex, ServerWebExchange exchange) {
        String parsedMessage = extractUsefulMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ErrorResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                        .path(exchange.getRequest().getPath().value())
                        .message("Invalid input: " + parsedMessage)
                        .errorCode("CATALOG-BAD-REQUEST-400")
                        .validationErrors(null)
                        .build()
        );
    }

    private String extractUsefulMessage(String msg) {
        if (msg == null) return "Unknown input error";
        msg = msg.toLowerCase();
        if (msg.contains("uuid")) return "Invalid UUID format.";
        if (msg.contains("localdate") || msg.contains("datetimeparseexception")) return "Invalid date format (expected yyyy-MM-dd).";
        if (msg.contains("bigdecimal")) return "Invalid number format (expected decimal).";
        if (msg.contains("integer")) return "Invalid number format (expected whole number).";
        if (msg.contains("json parse error")) return "Malformed JSON.";
        return "Unexpected input error.";
    }

    // === FALLBACK ===
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex, ServerWebExchange exchange) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ErrorResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                        .path(exchange.getRequest().getPath().value())
                        .message(ex.getMessage())
                        .errorCode("CATALOG-VALIDATION-001")
                        .validationErrors(null)
                        .build()
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneral(Exception ex, ServerWebExchange exchange) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                ErrorResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                        .path(exchange.getRequest().getPath().value())
                        .message(ex.getMessage())
                        .errorCode("CATALOG-ERROR-500")
                        .validationErrors(null)
                        .build()
        );
    }
}